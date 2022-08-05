package io.capawesome.capacitorjs.plugins.firebase.authentication;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.AppleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.FacebookAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.GoogleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.OAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PhoneAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PlayGamesAuthProviderHandler;
import java.util.Arrays;
import java.util.List;

public class FirebaseAuthentication {

    interface AuthStateChangeListener {
        void onAuthStateChanged();
    }

    @Nullable
    private AuthStateChangeListener authStateChangeListener;

    private FirebaseAuthenticationPlugin plugin;
    private FirebaseAuthenticationConfig config;
    private FirebaseAuth firebaseAuthInstance;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private AppleAuthProviderHandler appleAuthProviderHandler;
    private FacebookAuthProviderHandler facebookAuthProviderHandler;
    private GoogleAuthProviderHandler googleAuthProviderHandler;
    private OAuthProviderHandler oAuthProviderHandler;
    private PhoneAuthProviderHandler phoneAuthProviderHandler;
    private PlayGamesAuthProviderHandler playGamesAuthProviderHandler;

    public FirebaseAuthentication(FirebaseAuthenticationPlugin plugin, FirebaseAuthenticationConfig config) {
        this.plugin = plugin;
        this.config = config;
        firebaseAuthInstance = FirebaseAuth.getInstance();
        this.initAuthProviderHandlers(config);
        this.firebaseAuthStateListener =
            firebaseAuth -> {
                if (authStateChangeListener != null) {
                    authStateChangeListener.onAuthStateChanged();
                }
            };
        firebaseAuthInstance.addAuthStateListener(this.firebaseAuthStateListener);
    }

    public void setAuthStateChangeListener(@Nullable AuthStateChangeListener listener) {
        this.authStateChangeListener = listener;
    }

    @Nullable
    public AuthStateChangeListener getAuthStateChangeListener() {
        return authStateChangeListener;
    }

    public void applyActionCode(@NonNull String oobCode, @NonNull Runnable callback) {
        firebaseAuthInstance
            .applyActionCode(oobCode)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void createUserWithEmailAndPassword(PluginCall call) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_SIGN_IN_SKIP_NATIVE_AUTH);
            return;
        }

        String email = call.getString("email");
        if (email == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_MISSING);
            return;
        }
        String password = call.getString("password");
        if (password == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_PASSWORD_MISSING);
            return;
        }

        firebaseAuthInstance
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        Log.d(FirebaseAuthenticationPlugin.TAG, "createUserWithEmailAndPassword succeeded.");
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Log.e(FirebaseAuthenticationPlugin.TAG, "createUserWithEmailAndPassword failed.", task.getException());
                        call.reject(FirebaseAuthenticationPlugin.ERROR_CREATE_USER_WITH_EMAIL_AND_PASSWORD_FAILED);
                    }
                }
            );
    }

    public void confirmPasswordReset(@NonNull String oobCode, @NonNull String newPassword, @NonNull Runnable callback) {
        firebaseAuthInstance
            .confirmPasswordReset(oobCode, newPassword)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return firebaseAuthInstance.getCurrentUser();
    }

    public void getIdToken(Boolean forceRefresh, final GetIdTokenResultCallback resultCallback) {
        FirebaseUser user = getCurrentUser();
        if (user == null) {
            resultCallback.error(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }
        Task<GetTokenResult> tokenResultTask = user.getIdToken(forceRefresh);
        tokenResultTask.addOnCompleteListener(
            task -> {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    resultCallback.success(token);
                } else {
                    String message = task.getException().getLocalizedMessage();
                    resultCallback.error(message);
                }
            }
        );
    }

    public void sendEmailVerification(FirebaseUser user, @NonNull Runnable callback) {
        user
            .sendEmailVerification()
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void sendPasswordResetEmail(@NonNull String email, @NonNull Runnable callback) {
        firebaseAuthInstance
            .sendPasswordResetEmail(email)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void setLanguageCode(String languageCode) {
        firebaseAuthInstance.setLanguageCode(languageCode);
    }

    public void signInWithApple(final PluginCall call) {
        appleAuthProviderHandler.signIn(call);
    }

    public void signInWithFacebook(final PluginCall call) {
        facebookAuthProviderHandler.signIn(call);
    }

    public void signInWithGithub(final PluginCall call) {
        oAuthProviderHandler.signIn(call, "github.com");
    }

    public void signInWithGoogle(final PluginCall call) {
        googleAuthProviderHandler.signIn(call);
    }

    public void signInWithMicrosoft(final PluginCall call) {
        oAuthProviderHandler.signIn(call, "microsoft.com");
    }

    public void signInWithPhoneNumber(final PluginCall call) {
        phoneAuthProviderHandler.signIn(call);
    }

    public void signInWithPlayGames(final PluginCall call) {
        playGamesAuthProviderHandler.signIn(call);
    }

    public void signInWithTwitter(final PluginCall call) {
        oAuthProviderHandler.signIn(call, "twitter.com");
    }

    public void signInWithYahoo(final PluginCall call) {
        oAuthProviderHandler.signIn(call, "yahoo.com");
    }

    public void signInWithCustomToken(final PluginCall call) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_CUSTOM_TOKEN_SKIP_NATIVE_AUTH);
            return;
        }

        String token = call.getString("token", "");

        firebaseAuthInstance
            .signInWithCustomToken(token)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        Log.d(FirebaseAuthenticationPlugin.TAG, "signInWithCustomToken succeeded.");
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Log.e(FirebaseAuthenticationPlugin.TAG, "signInWithCustomToken failed.", task.getException());
                        call.reject(FirebaseAuthenticationPlugin.ERROR_SIGN_IN_FAILED);
                    }
                }
            );
    }

    public void signInWithEmailAndPassword(final PluginCall call) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_SIGN_IN_SKIP_NATIVE_AUTH);
            return;
        }

        String email = call.getString("email", "");
        String password = call.getString("password", "");

        firebaseAuthInstance
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        Log.d(FirebaseAuthenticationPlugin.TAG, "signInWithEmailAndPassword succeeded.");
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Log.e(FirebaseAuthenticationPlugin.TAG, "signInWithEmailAndPassword failed.", task.getException());
                        call.reject(FirebaseAuthenticationPlugin.ERROR_SIGN_IN_FAILED);
                    }
                }
            );
    }

    public void signOut(final PluginCall call) {
        FirebaseAuth.getInstance().signOut();
        if (googleAuthProviderHandler != null) {
            googleAuthProviderHandler.signOut();
        }
        if (facebookAuthProviderHandler != null) {
            facebookAuthProviderHandler.signOut();
        }
        if (playGamesAuthProviderHandler != null) {
            playGamesAuthProviderHandler.signOut();
        }
        call.resolve();
    }

    public void updateEmail(FirebaseUser user, @NonNull String newEmail, @NonNull Runnable callback) {
        user
            .updateEmail(newEmail)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void updatePassword(FirebaseUser user, @NonNull String newPassword, @NonNull Runnable callback) {
        user
            .updatePassword(newPassword)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void useAppLanguage() {
        firebaseAuthInstance.useAppLanguage();
    }

    public void useEmulator(@NonNull String host, int port) {
        firebaseAuthInstance.useEmulator(host, port);
    }

    public void startActivityForResult(final PluginCall call, Intent intent, String callbackName) {
        plugin.startActivityForResult(call, intent, callbackName);
    }

    public void handleGoogleAuthProviderActivityResult(final PluginCall call, ActivityResult result) {
        googleAuthProviderHandler.handleOnActivityResult(call, result);
    }

    public void handlePlayGamesAuthProviderActivityResult(final PluginCall call, ActivityResult result) {
        playGamesAuthProviderHandler.handleOnActivityResult(call, result);
    }

    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FacebookAuthProviderHandler.RC_FACEBOOK_AUTH && facebookAuthProviderHandler != null) {
            facebookAuthProviderHandler.handleOnActivityResult(requestCode, resultCode, data);
        }
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable AdditionalUserInfo additionalUserInfo
    ) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
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
        firebaseAuthInstance
            .signInWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
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
                        call.reject(FirebaseAuthenticationPlugin.ERROR_SIGN_IN_FAILED);
                    }
                }
            );
    }

    public void handleFailedSignIn(final PluginCall call, String message, Exception exception) {
        if (message == null && exception != null) {
            message = exception.getLocalizedMessage();
        }
        call.reject(message, exception);
    }

    public FirebaseAuth getFirebaseAuthInstance() {
        return firebaseAuthInstance;
    }

    public FirebaseAuthenticationPlugin getPlugin() {
        return plugin;
    }

    public FirebaseAuthenticationConfig getConfig() {
        return config;
    }

    private void initAuthProviderHandlers(FirebaseAuthenticationConfig config) {
        List providerList = Arrays.asList(config.getProviders());
        if (providerList.contains("facebook.com")) {
            facebookAuthProviderHandler = new FacebookAuthProviderHandler(this);
        }
        if (providerList.contains("google.com")) {
            googleAuthProviderHandler = new GoogleAuthProviderHandler(this);
        }
        if (providerList.contains("apple.com")) {
            appleAuthProviderHandler = new AppleAuthProviderHandler(this);
        }
        if (providerList.contains("playgames.google.com")) {
            playGamesAuthProviderHandler = new PlayGamesAuthProviderHandler(this);
        }
        if (providerList.contains("phone")) {
            phoneAuthProviderHandler = new PhoneAuthProviderHandler(this);
        }
        oAuthProviderHandler = new OAuthProviderHandler(this);
    }
}
