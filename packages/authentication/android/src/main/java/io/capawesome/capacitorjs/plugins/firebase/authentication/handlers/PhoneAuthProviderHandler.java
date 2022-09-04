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
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.util.concurrent.TimeUnit;

public class PhoneAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    public PhoneAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void link(final PluginCall call) {
        if (pluginImplementation.getCurrentUser() == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumberLink(call, phoneNumber);
        } else {
            handleVerificationCodeLink(call, verificationId, verificationCode);
        }
    }

    public void signIn(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumberSignIn(call, phoneNumber);
        } else {
            handleVerificationCodeSignIn(call, verificationId, verificationCode);
        }
    }

    private void verifyPhoneNumberLink(final PluginCall call, String phoneNumber) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacksLink(call));
        PhoneAuthOptions options = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleVerificationCodeLink(final PluginCall call, String verificationId, String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        pluginImplementation.handleSuccessfulLink(call, credential, null, null, null, null);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacksLink(final PluginCall call) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                pluginImplementation.handleSuccessfulLink(call, credential, null, null, null, null);
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                pluginImplementation.handleFailedLink(call, null, exception);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                JSObject result = FirebaseAuthenticationHelper.createSignInResult(null, null, null, null, null, null);
                result.put("verificationId", verificationId);
                call.resolve(result);
            }
        };
    }

    private void verifyPhoneNumberSignIn(final PluginCall call, String phoneNumber) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacksSignIn(call));
        PhoneAuthOptions options = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleVerificationCodeSignIn(final PluginCall call, String verificationId, String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        pluginImplementation.handleSuccessfulSignIn(call, credential, null, null, null, null);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacksSignIn(final PluginCall call) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                pluginImplementation.handleSuccessfulSignIn(call, credential, null, null, null, null);
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                pluginImplementation.handleFailedSignIn(call, null, exception);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                JSObject result = FirebaseAuthenticationHelper.createSignInResult(null, null, null, null, null, null);
                result.put("verificationId", verificationId);
                call.resolve(result);
            }
        };
    }
}
