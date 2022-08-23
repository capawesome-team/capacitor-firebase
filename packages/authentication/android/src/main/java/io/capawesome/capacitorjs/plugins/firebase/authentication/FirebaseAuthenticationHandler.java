package io.capawesome.capacitorjs.plugins.firebase.authentication;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper.createSignInResult;

import android.util.Log;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

public class FirebaseAuthenticationHandler {

    private FirebaseAuthentication implementation;

    public AuthHandler authHandlerLink;
    public AuthHandler authHandlerSignIn;

    public FirebaseAuthenticationHandler(FirebaseAuthentication implementation) {
        this.implementation = implementation;
        this.authHandlerLink = new AuthHandlerLink();
        this.authHandlerSignIn = new AuthHandlerSignIn();
    }

    public interface AuthHandler {
        void success(
            final PluginCall call,
            @Nullable AuthCredential credential,
            @Nullable String idToken,
            @Nullable String nonce,
            @Nullable String accessToken,
            @Nullable AdditionalUserInfo additionalUserInfo
        );

        default void failure(final PluginCall call, String message, Exception exception) {
            if (message == null && exception != null) {
                message = exception.getLocalizedMessage();
            }
            call.reject(message, exception);
        }
    }

    public class AuthHandlerLink implements AuthHandler {

        public void success(
            final PluginCall call,
            @Nullable AuthCredential credential,
            @Nullable String idToken,
            @Nullable String nonce,
            @Nullable String accessToken,
            @Nullable AdditionalUserInfo additionalUserInfo
        ) {
            boolean skipNativeAuth = implementation.getConfig().getSkipNativeAuth();
            if (skipNativeAuth) {
                JSObject linkResult = createSignInResult(null, credential, idToken, nonce, accessToken, additionalUserInfo);
                call.resolve(linkResult);
                return;
            }
            implementation
                .getFirebaseAuthInstance()
                .getCurrentUser()
                .linkWithCredential(credential)
                .addOnCompleteListener(
                    implementation.getPlugin().getActivity(),
                    task -> {
                        if (task.isSuccessful()) {
                            Log.d(FirebaseAuthenticationPlugin.TAG, "linkWithCredential succeeded.");
                            AuthResult authResult = task.getResult();
                            JSObject linkResult = createSignInResult(
                                authResult.getUser(),
                                authResult.getCredential(),
                                idToken,
                                nonce,
                                accessToken,
                                authResult.getAdditionalUserInfo()
                            );
                            call.resolve(linkResult);
                        } else {
                            Log.e(FirebaseAuthenticationPlugin.TAG, "linkWithCredential failed.", task.getException());
                            call.reject(task.getException().getLocalizedMessage());
                        }
                    }
                );
        }
    }

    public class AuthHandlerSignIn implements AuthHandler {

        public void success(
            final PluginCall call,
            @Nullable AuthCredential credential,
            @Nullable String idToken,
            @Nullable String nonce,
            @Nullable String accessToken,
            @Nullable AdditionalUserInfo additionalUserInfo
        ) {
            boolean skipNativeAuth = implementation.getConfig().getSkipNativeAuth();
            if (skipNativeAuth) {
                JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
                    null,
                    credential,
                    idToken,
                    nonce,
                    accessToken,
                    additionalUserInfo
                );
                call.resolve(signInResult);
                return;
            }
            implementation
                .getFirebaseAuthInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(
                    implementation.getPlugin().getActivity(),
                    task -> {
                        if (task.isSuccessful()) {
                            Log.d(FirebaseAuthenticationPlugin.TAG, "signInWithCredential succeeded.");
                            AuthResult authResult = task.getResult();
                            JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
                                authResult.getUser(),
                                authResult.getCredential(),
                                idToken,
                                nonce,
                                accessToken,
                                authResult.getAdditionalUserInfo()
                            );
                            call.resolve(signInResult);
                        } else {
                            Log.e(FirebaseAuthenticationPlugin.TAG, "signInWithCredential failed.", task.getException());
                            call.reject(task.getException().getLocalizedMessage());
                        }
                    }
                );
        }
    }
}
