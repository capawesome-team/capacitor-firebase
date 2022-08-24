package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHandler.*;

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

    public void link(final PluginCall call) {
        dispatch(call, AuthType.LINK);
    }

    public void signIn(final PluginCall call) {
        dispatch(call, AuthType.SIGN_IN);
    }

    private void dispatch(final PluginCall call, AuthType authType) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (verificationCode == null) {
            verifyPhoneNumber(call, authType, phoneNumber);
        } else {
            handleVerificationCode(call, authType, verificationId, verificationCode);
        }
    }

    private void verifyPhoneNumber(final PluginCall call, final AuthType authType, String phoneNumber) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions
            .newBuilder(pluginImplementation.getFirebaseAuthInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(pluginImplementation.getPlugin().getActivity())
            .setCallbacks(createCallbacks(call, authType));
        PhoneAuthOptions options = builder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleVerificationCode(final PluginCall call, final AuthType authType, String verificationId, String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        success(call, authType, pluginImplementation, credential, null, null, null, null);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacks(final PluginCall call, final AuthType authType) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                success(call, authType, pluginImplementation, credential, null, null, null, null);
            }

            @Override
            public void onVerificationFailed(FirebaseException exception) {
                failure(call, null, exception);
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
