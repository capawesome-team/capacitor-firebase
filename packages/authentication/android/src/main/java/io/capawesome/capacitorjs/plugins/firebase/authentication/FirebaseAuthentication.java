package io.capawesome.capacitorjs.plugins.firebase.authentication;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin.TAG;

import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper.ProviderId;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.ConfirmVerificationCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.GetIdTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.LinkWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.PhoneVerificationCompletedEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.AppleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.FacebookAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.GoogleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.OAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PhoneAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PlayGamesAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.ResultCallback;
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
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
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
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
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

    public void confirmVerificationCode(@NonNull ConfirmVerificationCodeOptions options, @NonNull ResultCallback callback) {
        phoneAuthProviderHandler.confirmVerificationCode(options, callback);
    }

    public void deleteUser(FirebaseUser user, @NonNull Runnable callback) {
        user
            .delete()
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

    public void getIdToken(Boolean forceRefresh, @NonNull final ResultCallback resultCallback) {
        FirebaseUser user = getCurrentUser();
        if (user == null) {
            resultCallback.error(new Exception(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN));
            return;
        }
        Task<GetTokenResult> tokenResultTask = user.getIdToken(forceRefresh);
        tokenResultTask.addOnCompleteListener(
            task -> {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    GetIdTokenResult result = new GetIdTokenResult(token);
                    resultCallback.success(result);
                } else {
                    Exception exception = task.getException();
                    resultCallback.error(exception);
                }
            }
        );
    }

    @Nullable
    public String getTenantId() {
        return firebaseAuthInstance.getTenantId();
    }

    public boolean isSignInWithEmailLink(@NonNull String emailLink) {
        return firebaseAuthInstance.isSignInWithEmailLink(emailLink);
    }

    public void linkWithApple(final PluginCall call) {
        appleAuthProviderHandler.link(call);
    }

    public void linkWithEmailAndPassword(final PluginCall call) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_LINK_SKIP_NATIVE_AUTH);
            return;
        }
        FirebaseUser user = getCurrentUser();
        if (user == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }

        String email = call.getString("email", "");
        String password = call.getString("password", "");

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        user
            .linkWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        JSObject linkResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(linkResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void linkWithEmailLink(final PluginCall call) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_LINK_SKIP_NATIVE_AUTH);
            return;
        }
        FirebaseUser user = getCurrentUser();
        if (user == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }

        String email = call.getString("email", "");
        String emailLink = call.getString("emailLink", "");

        AuthCredential credential = EmailAuthProvider.getCredentialWithLink(email, emailLink);

        user
            .linkWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        JSObject linkResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(linkResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void linkWithFacebook(final PluginCall call) {
        facebookAuthProviderHandler.link(call);
    }

    public void linkWithGithub(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.GITHUB);
    }

    public void linkWithGoogle(final PluginCall call) {
        googleAuthProviderHandler.link(call);
    }

    public void linkWithMicrosoft(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.MICROSOFT);
    }

    public void linkWithPhoneNumber(@NonNull final LinkWithPhoneNumberOptions options) throws Exception {
        phoneAuthProviderHandler.link(options);
    }

    public void linkWithPlayGames(final PluginCall call) {
        playGamesAuthProviderHandler.link(call);
    }

    public void linkWithTwitter(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.TWITTER);
    }

    public void linkWithYahoo(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.YAHOO);
    }

    public void reload(FirebaseUser user, @NonNull Runnable callback) {
        user
            .reload()
            .addOnCompleteListener(
                task -> {
                    callback.run();
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

    public void sendSignInLinkToEmail(@NonNull String email, @NonNull ActionCodeSettings actionCodeSettings, @NonNull Runnable callback) {
        firebaseAuthInstance
            .sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener(
                task -> {
                    callback.run();
                }
            );
    }

    public void setLanguageCode(String languageCode) {
        firebaseAuthInstance.setLanguageCode(languageCode);
    }

    public void setTenantId(String tenantId) {
        firebaseAuthInstance.setTenantId(tenantId);
    }

    public void signInAnonymously(final PluginCall call) {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_SIGN_IN_ANONYMOUSLY_SKIP_NATIVE_AUTH);
            return;
        }
        firebaseAuthInstance
            .signInAnonymously()
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        AuthResult authResult = task.getResult();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
                            authResult.getUser(),
                            authResult.getCredential(),
                            null,
                            null,
                            null,
                            authResult.getAdditionalUserInfo()
                        );
                        call.resolve(signInResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void signInWithApple(final PluginCall call) {
        appleAuthProviderHandler.signIn(call);
    }

    public void signInWithEmailAndPassword(final PluginCall call) {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
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
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void signInWithEmailLink(final PluginCall call) {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
        if (skipNativeAuth) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_EMAIL_LINK_SIGN_IN_SKIP_NATIVE_AUTH);
            return;
        }

        String email = call.getString("email", "");
        String emailLink = call.getString("emailLink", "");

        firebaseAuthInstance
            .signInWithEmailLink(email, emailLink)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        AuthResult authResult = task.getResult();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
                            authResult.getUser(),
                            authResult.getCredential(),
                            null,
                            null,
                            null,
                            authResult.getAdditionalUserInfo()
                        );
                        call.resolve(signInResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void signInWithFacebook(final PluginCall call) {
        facebookAuthProviderHandler.signIn(call);
    }

    public void signInWithGithub(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.GITHUB);
    }

    public void signInWithGoogle(final PluginCall call) {
        googleAuthProviderHandler.signIn(call);
    }

    public void signInWithMicrosoft(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.MICROSOFT);
    }

    public void signInWithPhoneNumber(final SignInWithPhoneNumberOptions options) throws Exception {
        phoneAuthProviderHandler.signIn(options);
    }

    public void signInWithPlayGames(final PluginCall call) {
        playGamesAuthProviderHandler.signIn(call);
    }

    public void signInWithTwitter(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.TWITTER);
    }

    public void signInWithYahoo(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.YAHOO);
    }

    public void signInWithCustomToken(final PluginCall call) {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
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
                        FirebaseUser user = getCurrentUser();
                        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(user, null, null, null, null, null);
                        call.resolve(signInResult);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
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

    public void unlink(final PluginCall call, FirebaseUser user, @NonNull String providerId) {
        user
            .unlink(providerId)
            .addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        AuthResult authResult = task.getResult();
                        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(authResult.getUser());
                        JSObject result = new JSObject();
                        result.put("user", userResult);
                        call.resolve(result);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
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

    public void updateProfile(FirebaseUser user, String displayName, String photoUrl, @NonNull Runnable callback) {
        UserProfileChangeRequest.Builder profileUpdates = new UserProfileChangeRequest.Builder();

        if (displayName != null) {
            profileUpdates.setDisplayName(displayName);
        }
        if (photoUrl != null) {
            profileUpdates.setPhotoUri(Uri.parse(photoUrl));
        }

        user
            .updateProfile(profileUpdates.build())
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

    public void handleGoogleAuthProviderSignInActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        googleAuthProviderHandler.handleOnActivityResult(call, result, false);
    }

    public void handleGoogleAuthProviderLinkActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        googleAuthProviderHandler.handleOnActivityResult(call, result, true);
    }

    public void handlePlayGamesAuthProviderSignInActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        playGamesAuthProviderHandler.handleOnActivityResult(call, result, false);
    }

    public void handlePlayGamesAuthProviderLinkActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        playGamesAuthProviderHandler.handleOnActivityResult(call, result, true);
    }

    public void handleOnActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        if (requestCode == FacebookAuthProviderHandler.RC_FACEBOOK_AUTH && facebookAuthProviderHandler != null) {
            facebookAuthProviderHandler.handleOnActivityResult(requestCode, resultCode, data);
        }
    }

    public void signInWithCredential(@NonNull SignInOptions options, @NonNull AuthCredential credential, @NonNull ResultCallback callback) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            SignInResult result = new SignInResult(null, credential, null);
            callback.success(result);
            return;
        }
        firebaseAuthInstance
            .signInWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        Logger.debug(TAG, "signInWithCredential succeeded.");
                        final AuthResult authResult = task.getResult();
                        SignInResult result = new SignInResult(authResult);
                        callback.success(result);
                    } else {
                        Logger.error(TAG, "signInWithCredential failed.", task.getException());
                        callback.error(task.getException());
                    }
                }
            );
    }

    public void linkWithCredential(@NonNull AuthCredential credential, @NonNull ResultCallback callback) {
        FirebaseUser user = firebaseAuthInstance.getCurrentUser();
        if (user == null) {
            callback.error(new Exception(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN));
            return;
        }
        user
            .linkWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        Logger.debug(TAG, "linkWithCredential succeeded.");
                        final AuthResult authResult = task.getResult();
                        SignInResult result = new SignInResult(authResult);
                        callback.success(result);
                    } else {
                        Logger.error(TAG, "linkWithCredential failed.", task.getException());
                        callback.error(task.getException());
                    }
                }
            );
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable AdditionalUserInfo additionalUserInfo
    ) {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
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
                        final AuthResult authResult = task.getResult();
                        handleSuccessfulSignIn(call, authResult, idToken, nonce, accessToken);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        final AuthResult authResult,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken
    ) {
        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
            authResult.getUser(),
            authResult.getCredential(),
            idToken,
            nonce,
            accessToken,
            authResult.getAdditionalUserInfo()
        );
        call.resolve(signInResult);
    }

    public void handleFailedSignIn(@NonNull final PluginCall call, @Nullable String message, @Nullable Exception exception) {
        if (message == null && exception != null) {
            message = exception.getMessage();
        }
        Logger.error(TAG, message, exception);
        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
        call.reject(message, code);
    }

    public void handleSuccessfulLink(
        final PluginCall call,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken
    ) {
        FirebaseUser user = firebaseAuthInstance.getCurrentUser();
        if (user == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }
        user
            .linkWithCredential(credential)
            .addOnCompleteListener(
                plugin.getActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        final AuthResult authResult = task.getResult();
                        handleSuccessfulLink(call, authResult, idToken, nonce, accessToken);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                }
            );
    }

    public void handleSuccessfulLink(
        final PluginCall call,
        final AuthResult authResult,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken
    ) {
        JSObject linkResult = FirebaseAuthenticationHelper.createSignInResult(
            authResult.getUser(),
            authResult.getCredential(),
            idToken,
            nonce,
            accessToken,
            authResult.getAdditionalUserInfo()
        );
        call.resolve(linkResult);
    }

    public void handleFailedLink(final PluginCall call, String message, Exception exception) {
        if (message == null && exception != null) {
            message = exception.getMessage();
        }
        Logger.error(TAG, message, exception);
        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
        call.reject(message, code);
    }

    public void handlePhoneVerificationCompleted(@NonNull final PhoneVerificationCompletedEvent event) {
        plugin.handlePhoneVerificationCompleted(event);
    }

    public void handlePhoneVerificationFailed(Exception exception) {
        plugin.handlePhoneVerificationFailed(exception);
    }

    public void handlePhoneCodeSent(String verificationId) {
        plugin.handlePhoneCodeSent(verificationId);
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
        if (providerList.contains(ProviderId.APPLE)) {
            appleAuthProviderHandler = new AppleAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.FACEBOOK)) {
            facebookAuthProviderHandler = new FacebookAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.GOOGLE)) {
            googleAuthProviderHandler = new GoogleAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.PHONE)) {
            phoneAuthProviderHandler = new PhoneAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.PLAY_GAMES)) {
            playGamesAuthProviderHandler = new PlayGamesAuthProviderHandler(this);
        }
        oAuthProviderHandler = new OAuthProviderHandler(this);
    }
}
