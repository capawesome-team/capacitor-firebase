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

    public enum AuthType {
        SIGN_IN,
        LINK
    }

    public static void success(
        final PluginCall call,
        final AuthType authType,
        final FirebaseAuthentication implementation,
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
        (
            (authType == AuthType.LINK)
                ? implementation.getCurrentUser().linkWithCredential(credential)
                : implementation.getFirebaseAuthInstance().signInWithCredential(credential)
        ).addOnCompleteListener(
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

    public static void failure(final PluginCall call, String message, Exception exception) {
        if (message == null && exception != null) {
            message = exception.getLocalizedMessage();
        }
        call.reject(message, exception);
    }
}
