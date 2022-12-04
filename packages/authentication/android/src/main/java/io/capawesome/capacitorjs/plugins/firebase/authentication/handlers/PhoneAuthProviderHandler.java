package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.util.concurrent.TimeUnit;

public class PhoneAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    @Nullable
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    public PhoneAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void signIn(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        boolean resendCode = call.getBoolean("resendCode", false);
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, phoneNumber, resendCode, false);
        } else {
            handleVerificationCode(call, verificationId, verificationCode, false);
        }
    }

    public void link(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        boolean resendCode = call.getBoolean("resendCode", false);
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, phoneNumber, resendCode, true);
        } else {
            handleVerificationCode(call, verificationId, verificationCode, true);
        }
    }

    private void verifyPhoneNumber(final PluginCall call, String phoneNumber, boolean resendCode, boolean isLink) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacks(call, isLink));
        if (resendCode) {
            if (forceResendingToken == null) {
                call.reject(FirebaseAuthenticationPlugin.ERROR_PHONE_RESEND_TOKEN_MISSING);
                return;
            }
            builder.setForceResendingToken(forceResendingToken);
        }
        PhoneAuthOptions options = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleVerificationCode(final PluginCall call, String verificationId, String verificationCode, boolean isLink) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        if (isLink) {
            pluginImplementation.handleSuccessfulLink(call, credential, null, null, null);
        } else {
            pluginImplementation.handleSuccessfulSignIn(call, credential, null, null, null, null);
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacks(final PluginCall call, boolean isLink) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                String smsCode = credential.getSmsCode();
                pluginImplementation.handlePhoneVerificationCompleted(smsCode);
                /**
                 * @deprecated This code was replaced by event listener.
                 *
                 * Caution: The call must be resolved earlier.
                 */
                if (isLink) {
                    pluginImplementation.handleSuccessfulLink(call, credential, null, null, null);
                } else {
                    pluginImplementation.handleSuccessfulSignIn(call, credential, null, null, null, null);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                pluginImplementation.handlePhoneVerificationFailed(exception);
                /**
                 * @deprecated This code was replaced by event listener.
                 *
                 * Caution: The call must be resolved earlier.
                 */
                if (isLink) {
                    pluginImplementation.handleFailedLink(call, null, exception);
                } else {
                    pluginImplementation.handleFailedSignIn(call, null, exception);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                forceResendingToken = token;
                pluginImplementation.handlePhoneCodeSent(verificationId);
                /**
                 * @deprecated This code was replaced by event listener.
                 *
                 * Caution: The call must be resolved earlier.
                 */
                JSObject result = FirebaseAuthenticationHelper.createSignInResult(null, null, null, null, null, null);
                result.put("verificationId", verificationId);
                call.resolve(result);
            }
        };
    }
}
