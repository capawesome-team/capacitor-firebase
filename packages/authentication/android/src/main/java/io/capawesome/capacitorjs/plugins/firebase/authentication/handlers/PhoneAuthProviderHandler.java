package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.ConfirmVerificationCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.LinkWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.SignInResultCallback;
import java.util.concurrent.TimeUnit;

public class PhoneAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    @Nullable
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private boolean signInOnConfirm = true;
    private boolean skipNativeAuthOnConfirm = false;

    public PhoneAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void signIn(@NonNull final SignInWithPhoneNumberOptions options) throws Exception {
        signInOnConfirm = true;
        skipNativeAuthOnConfirm = options.getSkipNativeAuth();
        verifyPhoneNumber(options, false);
    }

    public void link(@NonNull final LinkWithPhoneNumberOptions options) throws Exception {
        signInOnConfirm = false;
        skipNativeAuthOnConfirm = options.getSkipNativeAuth();
        verifyPhoneNumber(options, true);
    }

    public void confirmVerificationCode(@NonNull ConfirmVerificationCodeOptions options, @NonNull SignInResultCallback callback) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(options.getVerificationId(), options.getVerificationCode());
        if (signInOnConfirm) {
            pluginImplementation.signInWithCredential(new SignInOptions(skipNativeAuthOnConfirm), callback, credential);
        } else {
            pluginImplementation.linkWithCredential(callback, credential);
        }
    }

    private void verifyPhoneNumber(@NonNull SignInWithPhoneNumberOptions options, boolean isLink) throws Exception {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(options.getPhoneNumber())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacks(options, isLink));
        if (options.getResendCode()) {
            if (forceResendingToken == null) {
                throw new Exception(FirebaseAuthenticationPlugin.ERROR_PHONE_RESEND_TOKEN_MISSING);
            }
            builder.setForceResendingToken(forceResendingToken);
        }
        PhoneAuthOptions phoneAuthOptions = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacks(
        @NonNull final SignInWithPhoneNumberOptions options,
        boolean isLink
    ) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                SignInResultCallback callback = new SignInResultCallback() {
                    @Override
                    public void success(SignInResult result) {
                        pluginImplementation.handlePhoneVerificationCompleted(credential);
                    }

                    @Override
                    public void error(Exception exception) {
                        pluginImplementation.handlePhoneVerificationFailed(exception);
                    }
                };
                if (isLink) {
                    pluginImplementation.linkWithCredential(callback, credential);
                } else {
                    pluginImplementation.signInWithCredential(options, callback, credential);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                pluginImplementation.handlePhoneVerificationFailed(exception);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                forceResendingToken = token;
                pluginImplementation.handlePhoneCodeSent(verificationId);
            }
        };
    }
}
