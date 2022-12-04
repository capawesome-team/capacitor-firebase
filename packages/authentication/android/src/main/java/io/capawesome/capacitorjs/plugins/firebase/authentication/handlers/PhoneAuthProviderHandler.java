package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;
import java.util.concurrent.TimeUnit;

public class PhoneAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    public PhoneAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void signIn(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, phoneNumber, false);
        } else {
            handleVerificationCode(call, verificationId, verificationCode, false);
        }
    }

    public void link(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, phoneNumber, true);
        } else {
            handleVerificationCode(call, verificationId, verificationCode, true);
        }
    }

    private void verifyPhoneNumber(final PluginCall call, String phoneNumber, boolean isLink) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacks(call, isLink));
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
