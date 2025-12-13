package app.capgo.capacitor.firebase.authentication.handlers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.authentication.FirebaseAuthentication;
import app.capgo.capacitor.firebase.authentication.FirebaseAuthenticationPlugin;
import app.capgo.capacitor.firebase.authentication.classes.ConfirmVerificationCodeOptions;
import app.capgo.capacitor.firebase.authentication.classes.LinkWithPhoneNumberOptions;
import app.capgo.capacitor.firebase.authentication.classes.PhoneVerificationCompletedEvent;
import app.capgo.capacitor.firebase.authentication.classes.SignInOptions;
import app.capgo.capacitor.firebase.authentication.classes.SignInResult;
import app.capgo.capacitor.firebase.authentication.classes.SignInWithPhoneNumberOptions;
import app.capgo.capacitor.firebase.authentication.interfaces.NonEmptyResultCallback;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
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

    public void confirmVerificationCode(@NonNull ConfirmVerificationCodeOptions options, @NonNull NonEmptyResultCallback callback) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(options.getVerificationId(), options.getVerificationCode());
        if (signInOnConfirm) {
            pluginImplementation.signInWithCredential(new SignInOptions(skipNativeAuthOnConfirm), credential, callback);
        } else {
            pluginImplementation.linkWithCredential(credential, callback);
        }
    }

    private void verifyPhoneNumber(@NonNull SignInWithPhoneNumberOptions options, boolean isLink) throws Exception {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(options.getPhoneNumber())
            .setTimeout(options.getTimeout(), TimeUnit.SECONDS)
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
                NonEmptyResultCallback callback = new NonEmptyResultCallback<SignInResult>() {
                    @Override
                    public void success(SignInResult result) {
                        PhoneVerificationCompletedEvent event = new PhoneVerificationCompletedEvent(
                            result.getUser(),
                            result.getCredential(),
                            result.getAdditionalUserInfo(),
                            credential.getSmsCode()
                        );
                        pluginImplementation.handlePhoneVerificationCompleted(event);
                    }

                    @Override
                    public void error(Exception exception) {
                        pluginImplementation.handlePhoneVerificationFailed(exception);
                    }
                };
                if (isLink) {
                    pluginImplementation.linkWithCredential(credential, callback);
                } else {
                    pluginImplementation.signInWithCredential(options, credential, callback);
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
