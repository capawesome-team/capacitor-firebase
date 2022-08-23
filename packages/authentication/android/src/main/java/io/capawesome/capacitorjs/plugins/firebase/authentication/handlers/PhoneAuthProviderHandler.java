package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHandler.*;

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
import java.util.concurrent.TimeUnit;

public class PhoneAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    @Nullable
    private AuthHandler handler;

    public PhoneAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void link(final PluginCall call) {
        this.handler = pluginImplementation.getAuthHandlerLink();
        dispatch(call);
    }

    public void signIn(final PluginCall call) {
        this.handler = pluginImplementation.getAuthHandlerSignIn();
        dispatch(call);
    }

    private void dispatch(final PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, phoneNumber);
        } else {
            handleVerificationCode(call, verificationId, verificationCode);
        }
    }

    private void verifyPhoneNumber(final PluginCall call, String phoneNumber) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacks(call));
        PhoneAuthOptions options = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleVerificationCode(final PluginCall call, String verificationId, String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        handler.success(call, credential, null, null, null, null);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacks(final PluginCall call) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                handler.success(call, credential, null, null, null, null);
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                handler.failure(call, null, exception);
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
