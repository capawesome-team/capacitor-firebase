package io.capawesome.capacitorjs.plugins.firebase.authentication;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN;
import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper.ProviderId;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.ConfirmVerificationCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.LinkWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.PhoneVerificationCompletedEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events.AuthStateChangeEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events.IdTokenChangeEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.ApplyActionCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.ConfirmPasswordResetOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.CreateUserWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.FetchSignInMethodsForEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetIdTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetIdTokenResultOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.IsSignInWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.LinkWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.LinkWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.RevokeAccessTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendEmailVerificationOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendPasswordResetEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendSignInLinkToEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SetLanguageCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SetTenantIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithCustomTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UnlinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdateEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdatePasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdateProfileOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UseEmulatorOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.VerifyBeforeUpdateEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.FetchSignInMethodsForEmailResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetCurrentUserResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetIdTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetIdTokenResultResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetTenantIdResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.IsSignInWithEmailLinkResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.UnlinkResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.AppleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.FacebookAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.GoogleAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.OAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PhoneAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.handlers.PlayGamesAuthProviderHandler;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.NonEmptyResultCallback;
import java.util.Arrays;
import java.util.List;

public class FirebaseAuthentication {

    public ActivityResultLauncher<IntentSenderRequest> googleAuthorizationResultLauncher;

    @Nullable
    private AppleAuthProviderHandler appleAuthProviderHandler;

    private FirebaseAuthenticationConfig config;

    @Nullable
    private FacebookAuthProviderHandler facebookAuthProviderHandler;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private FirebaseAuth.IdTokenListener firebaseIdTokenChangeListener;

    @Nullable
    private GoogleAuthProviderHandler googleAuthProviderHandler;

    private OAuthProviderHandler oAuthProviderHandler;

    @Nullable
    private PhoneAuthProviderHandler phoneAuthProviderHandler;

    @Nullable
    private PlayGamesAuthProviderHandler playGamesAuthProviderHandler;

    private FirebaseAuthenticationPlugin plugin;

    public FirebaseAuthentication(FirebaseAuthenticationPlugin plugin, FirebaseAuthenticationConfig config) {
        this.plugin = plugin;
        this.config = config;
        this.initAuthProviderHandlers(config);
        this.firebaseAuthStateListener = firebaseAuth -> {
            this.handleAuthStateChange();
        };
        getFirebaseAuthInstance().addAuthStateListener(this.firebaseAuthStateListener);
        this.firebaseIdTokenChangeListener = firebaseAuth -> {
            this.handleIdTokenChange();
        };
        getFirebaseAuthInstance().addIdTokenListener(this.firebaseIdTokenChangeListener);
        if (config.getAuthDomain() != null && !config.getAuthDomain().isEmpty()) {
            getFirebaseAuthInstance().setCustomAuthDomain(config.getAuthDomain());
        }
    }

    public void applyActionCode(@NonNull ApplyActionCodeOptions options, @NonNull EmptyResultCallback callback) {
        String oobCode = options.getOobCode();

        getFirebaseAuthInstance()
            .applyActionCode(oobCode)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void confirmPasswordReset(@NonNull ConfirmPasswordResetOptions options, @NonNull EmptyResultCallback callback) {
        String oobCode = options.getOobCode();
        String newPassword = options.getNewPassword();

        getFirebaseAuthInstance()
            .confirmPasswordReset(oobCode, newPassword)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void confirmVerificationCode(@NonNull ConfirmVerificationCodeOptions options, @NonNull NonEmptyResultCallback callback) {
        if (phoneAuthProviderHandler == null) {
            callback.error(new Exception(createProviderNotEnabledErrorMessage("Phone")));
            return;
        }
        phoneAuthProviderHandler.confirmVerificationCode(options, callback);
    }

    public void createUserWithEmailAndPassword(
        @NonNull CreateUserWithEmailAndPasswordOptions options,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        String email = options.getEmail();
        String password = options.getPassword();

        getFirebaseAuthInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
                    SignInResult result = new SignInResult(user, null, null);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public void deleteUser(@NonNull EmptyResultCallback callback) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        user.delete()
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void destroy() {
        if (this.firebaseAuthStateListener != null) {
            getFirebaseAuthInstance().removeAuthStateListener(this.firebaseAuthStateListener);
            this.firebaseAuthStateListener = null;
        }
        if (this.firebaseIdTokenChangeListener != null) {
            getFirebaseAuthInstance().removeIdTokenListener(this.firebaseIdTokenChangeListener);
            this.firebaseIdTokenChangeListener = null;
        }
    }

    public void fetchSignInMethodsForEmail(
        @NonNull FetchSignInMethodsForEmailOptions options,
        @NonNull final NonEmptyResultCallback<FetchSignInMethodsForEmailResult> callback
    ) {
        String email = options.getEmail();

        getFirebaseAuthInstance()
            .fetchSignInMethodsForEmail(email)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<String> signInMethods = task.getResult().getSignInMethods();
                    FetchSignInMethodsForEmailResult result = new FetchSignInMethodsForEmailResult(signInMethods);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public FirebaseAuthenticationConfig getConfig() {
        return config;
    }

    public void getCurrentUser(@NonNull NonEmptyResultCallback<GetCurrentUserResult> callback) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        GetCurrentUserResult result = new GetCurrentUserResult(user);
        callback.success(result);
    }

    public FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    public void getIdToken(@NonNull GetIdTokenOptions options, @NonNull NonEmptyResultCallback<GetIdTokenResult> callback) {
        getIdToken(options.getForceRefresh(), callback);
    }

    public void getIdTokenResult(
        @NonNull GetIdTokenResultOptions options,
        @NonNull final NonEmptyResultCallback<GetIdTokenResultResult> callback
    ) {
        boolean forceRefresh = options.getForceRefresh();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        Task<GetTokenResult> idTokenResultTask = user.getIdToken(forceRefresh);
        idTokenResultTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                GetTokenResult tokenResult = task.getResult();
                GetIdTokenResultResult result = new GetIdTokenResultResult(tokenResult);
                callback.success(result);
            } else {
                Exception exception = task.getException();
                callback.error(exception);
            }
        });
    }

    public void getPendingAuthResult(PluginCall call) {
        oAuthProviderHandler.getPendingAuthResult(call);
    }

    public FirebaseAuthenticationPlugin getPlugin() {
        return plugin;
    }

    public void getTenantId(@NonNull NonEmptyResultCallback<GetTenantIdResult> callback) {
        String tenantId = getFirebaseAuthInstance().getTenantId();
        GetTenantIdResult result = new GetTenantIdResult(tenantId);
        callback.success(result);
    }

    public void handleFailedLink(final PluginCall call, @Nullable String message, Exception exception) {
        if (message == null && exception != null) {
            message = exception.getMessage();
        }
        Logger.error(TAG, message, exception);
        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
        call.reject(message, code);
    }

    public void handleFailedSignIn(@NonNull final PluginCall call, @Nullable String message, @Nullable Exception exception) {
        if (message == null && exception != null) {
            message = exception.getMessage();
        }
        Logger.error(TAG, message, exception);
        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
        call.reject(message, code);
    }

    public void handleGoogleAuthProviderLinkActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        if (googleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Google"));
            return;
        }
        googleAuthProviderHandler.handleOnActivityResult(call, result, true);
    }

    public void handleGoogleAuthProviderSignInActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        if (googleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Google"));
            return;
        }
        googleAuthProviderHandler.handleOnActivityResult(call, result, false);
    }

    public void handlePhoneCodeSent(String verificationId) {
        plugin.handlePhoneCodeSent(verificationId);
    }

    public void handlePhoneVerificationCompleted(@NonNull final PhoneVerificationCompletedEvent event) {
        plugin.handlePhoneVerificationCompleted(event);
    }

    public void handlePhoneVerificationFailed(Exception exception) {
        plugin.handlePhoneVerificationFailed(exception);
    }

    public void handlePlayGamesAuthProviderLinkActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        if (playGamesAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Play Games"));
            return;
        }
        playGamesAuthProviderHandler.handleOnActivityResult(call, result, true);
    }

    public void handlePlayGamesAuthProviderSignInActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result) {
        if (playGamesAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Play Games"));
            return;
        }
        playGamesAuthProviderHandler.handleOnActivityResult(call, result, false);
    }

    public void handleSuccessfulLink(
        final PluginCall call,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable String serverAuthCode
    ) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            call.reject(ERROR_NO_USER_SIGNED_IN);
            return;
        }
        if (credential == null) {
            handleSuccessfulLink(call);
        } else {
            user.linkWithCredential(credential).addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    final AuthResult authResult = task.getResult();
                    handleSuccessfulLink(call, authResult, idToken, nonce, accessToken, serverAuthCode);
                } else {
                    Exception exception = task.getException();
                    Logger.error(TAG, exception.getMessage(), exception);
                    String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                    call.reject(exception.getMessage(), code);
                }
            });
        }
    }

    public void handleSuccessfulLink(
        final PluginCall call,
        @Nullable AuthResult authResult,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable String serverAuthCode
    ) {
        if (authResult == null) {
            handleSuccessfulLink(call);
        } else {
            JSObject linkResult = FirebaseAuthenticationHelper.createSignInResult(
                authResult.getUser(),
                authResult.getCredential(),
                idToken,
                nonce,
                accessToken,
                serverAuthCode,
                authResult.getAdditionalUserInfo()
            );
            call.resolve(linkResult);
        }
    }

    public void handleSuccessfulLink(final PluginCall call) {
        JSObject linkResult = FirebaseAuthenticationHelper.createSignInResult(null, null, null, null, null, null);
        call.resolve(linkResult);
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable String serverAuthCode,
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
                serverAuthCode,
                additionalUserInfo
            );
            call.resolve(signInResult);
            return;
        }
        if (credential == null) {
            handleSuccessfulSignIn(call);
        } else {
            getFirebaseAuthInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(plugin.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        final AuthResult authResult = task.getResult();
                        handleSuccessfulSignIn(call, authResult, idToken, nonce, accessToken, serverAuthCode);
                    } else {
                        Exception exception = task.getException();
                        Logger.error(TAG, exception.getMessage(), exception);
                        String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                        call.reject(exception.getMessage(), code);
                    }
                });
        }
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        @Nullable AuthResult authResult,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken
    ) {
        if (authResult == null) {
            handleSuccessfulSignIn(call);
        } else {
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
    }

    public void handleSuccessfulSignIn(
        final PluginCall call,
        @Nullable AuthResult authResult,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable String serverAuthCode
    ) {
        if (authResult == null) {
            handleSuccessfulSignIn(call);
        } else {
            JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(
                authResult.getUser(),
                authResult.getCredential(),
                idToken,
                nonce,
                accessToken,
                serverAuthCode,
                authResult.getAdditionalUserInfo()
            );
            call.resolve(signInResult);
        }
    }

    public void handleSuccessfulSignIn(@NonNull final PluginCall call) {
        JSObject signInResult = FirebaseAuthenticationHelper.createSignInResult(null, null, null, null, null, null);
        call.resolve(signInResult);
    }

    // Firebase's GenericIdpActivity crashes with an ActivityNotFoundException instead of failing the task when no browser is installed.
    public boolean isBrowserAvailable() {
        // A scheme-only URI is resolved by general-purpose browsers only, not by apps registered for specific hosts.
        Uri probeUri = Uri.fromParts("https", "", null);
        Intent intent = new Intent(Intent.ACTION_VIEW, probeUri).addCategory(Intent.CATEGORY_BROWSABLE);
        return intent.resolveActivity(plugin.getContext().getPackageManager()) != null;
    }

    public void isSignInWithEmailLink(
        @NonNull IsSignInWithEmailLinkOptions options,
        @NonNull NonEmptyResultCallback<IsSignInWithEmailLinkResult> callback
    ) {
        String emailLink = options.getEmailLink();

        boolean isSignInWithEmailLink = getFirebaseAuthInstance().isSignInWithEmailLink(emailLink);
        IsSignInWithEmailLinkResult result = new IsSignInWithEmailLinkResult(isSignInWithEmailLink);
        callback.success(result);
    }

    public void linkWithApple(final PluginCall call) {
        if (appleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Apple"));
            return;
        }
        appleAuthProviderHandler.link(call);
    }

    public void linkWithCredential(@NonNull AuthCredential credential, @NonNull NonEmptyResultCallback callback) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(new Exception(ERROR_NO_USER_SIGNED_IN));
            return;
        }
        user.linkWithCredential(credential).addOnCompleteListener(plugin.getActivity(), task -> {
            if (task.isSuccessful()) {
                Logger.debug(TAG, "linkWithCredential succeeded.");
                final AuthResult authResult = task.getResult();
                SignInResult result = new SignInResult(authResult);
                callback.success(result);
            } else {
                Logger.error(TAG, "linkWithCredential failed.", task.getException());
                callback.error(task.getException());
            }
        });
    }

    public void linkWithEmailAndPassword(
        @NonNull LinkWithEmailAndPasswordOptions options,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.EMAIL_LINK_SKIP_NATIVE_AUTH);
            return;
        }
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }

        String email = options.getEmail();
        String password = options.getPassword();

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        user.linkWithCredential(credential).addOnCompleteListener(plugin.getActivity(), task -> {
            if (task.isSuccessful()) {
                SignInResult result = new SignInResult(user, null, null);
                callback.success(result);
            } else {
                Exception exception = task.getException();
                callback.error(exception);
            }
        });
    }

    public void linkWithEmailLink(@NonNull LinkWithEmailLinkOptions options, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        boolean skipNativeAuth = this.config.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.EMAIL_LINK_SKIP_NATIVE_AUTH);
            return;
        }
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }

        String email = options.getEmail();
        String emailLink = options.getEmailLink();

        AuthCredential credential = EmailAuthProvider.getCredentialWithLink(email, emailLink);

        user.linkWithCredential(credential).addOnCompleteListener(plugin.getActivity(), task -> {
            if (task.isSuccessful()) {
                SignInResult result = new SignInResult(user, null, null);
                callback.success(result);
            } else {
                Exception exception = task.getException();
                callback.error(exception);
            }
        });
    }

    public void linkWithFacebook(final PluginCall call) {
        if (facebookAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Facebook"));
            return;
        }
        facebookAuthProviderHandler.link(call);
    }

    public void linkWithGithub(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.GITHUB);
    }

    public void linkWithGoogle(final PluginCall call) {
        if (googleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Google"));
            return;
        }
        googleAuthProviderHandler.link(call);
    }

    public void linkWithMicrosoft(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.MICROSOFT);
    }

    public void linkWithOpenIdConnect(final PluginCall call, final String providerId) {
        oAuthProviderHandler.link(call, providerId);
    }

    public void linkWithPhoneNumber(@NonNull final LinkWithPhoneNumberOptions options) throws Exception {
        if (phoneAuthProviderHandler == null) {
            throw new Exception(createProviderNotEnabledErrorMessage("Phone"));
        }
        phoneAuthProviderHandler.link(options);
    }

    public void linkWithPlayGames(final PluginCall call) {
        if (playGamesAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Play Games"));
            return;
        }
        playGamesAuthProviderHandler.link(call);
    }

    public void linkWithTwitter(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.TWITTER);
    }

    public void linkWithYahoo(final PluginCall call) {
        oAuthProviderHandler.link(call, ProviderId.YAHOO);
    }

    public void reload(@NonNull EmptyResultCallback callback) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        user.reload()
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void revokeAccessToken(@NonNull RevokeAccessTokenOptions options, @NonNull EmptyResultCallback callback) {
        String token = options.getToken();

        getFirebaseAuthInstance()
            .revokeAccessToken(token)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void sendEmailVerification(@NonNull SendEmailVerificationOptions options, @NonNull EmptyResultCallback callback)
        throws Exception {
        ActionCodeSettings actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.getActionCodeSettings());

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }

        Task<Void> task = null;
        if (actionCodeSettings == null) {
            task = user.sendEmailVerification();
        } else {
            task = user.sendEmailVerification(actionCodeSettings);
        }
        task.addOnSuccessListener(unused -> callback.success()).addOnFailureListener(exception -> callback.error(exception));
    }

    public void sendPasswordResetEmail(@NonNull SendPasswordResetEmailOptions options, @NonNull EmptyResultCallback callback)
        throws Exception {
        String email = options.getEmail();
        ActionCodeSettings actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.getActionCodeSettings());

        getFirebaseAuthInstance()
            .sendPasswordResetEmail(email, actionCodeSettings)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void sendSignInLinkToEmail(@NonNull SendSignInLinkToEmailOptions options, @NonNull EmptyResultCallback callback)
        throws Exception {
        String email = options.getEmail();
        ActionCodeSettings actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.getActionCodeSettings());

        getFirebaseAuthInstance()
            .sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void setLanguageCode(@NonNull SetLanguageCodeOptions options, @NonNull EmptyResultCallback callback) {
        String languageCode = options.getLanguageCode();

        getFirebaseAuthInstance().setLanguageCode(languageCode);
        callback.success();
    }

    public void setTenantId(@NonNull SetTenantIdOptions options, @NonNull EmptyResultCallback callback) {
        String tenantId = options.getTenantId();

        getFirebaseAuthInstance().setTenantId(tenantId);
        callback.success();
    }

    public void signInAnonymously(@NonNull SignInOptions options, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.SIGN_IN_ANONYMOUSLY_SKIP_NATIVE_AUTH);
            return;
        }
        getFirebaseAuthInstance()
            .signInAnonymously()
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    AuthResult authResult = task.getResult();
                    SignInResult result = new SignInResult(authResult);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public void signInWithApple(final PluginCall call) {
        if (appleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Apple"));
            return;
        }
        appleAuthProviderHandler.signIn(call);
    }

    public void signInWithCredential(
        @NonNull SignInOptions options,
        @NonNull AuthCredential credential,
        @NonNull NonEmptyResultCallback callback
    ) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            SignInResult result = new SignInResult(null, credential, null);
            callback.success(result);
            return;
        }
        getFirebaseAuthInstance()
            .signInWithCredential(credential)
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    Logger.debug(TAG, "signInWithCredential succeeded.");
                    final AuthResult authResult = task.getResult();
                    SignInResult result = new SignInResult(authResult);
                    callback.success(result);
                } else {
                    Logger.error(TAG, "signInWithCredential failed.", task.getException());
                    callback.error(task.getException());
                }
            });
    }

    public void signInWithCustomToken(
        @NonNull SignInWithCustomTokenOptions options,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.CUSTOM_TOKEN_SKIP_NATIVE_AUTH);
            return;
        }

        String token = options.getToken();

        getFirebaseAuthInstance()
            .signInWithCustomToken(token)
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
                    SignInResult result = new SignInResult(user, null, null);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public void signInWithEmailAndPassword(
        @NonNull SignInWithEmailAndPasswordOptions options,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.EMAIL_SIGN_IN_SKIP_NATIVE_AUTH);
            return;
        }

        String email = options.getEmail();
        String password = options.getPassword();

        getFirebaseAuthInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
                    SignInResult result = new SignInResult(user, null, null);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public void signInWithEmailLink(@NonNull SignInWithEmailLinkOptions options, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        boolean skipNativeAuth = options.getSkipNativeAuth();
        if (skipNativeAuth) {
            callback.error(CustomExceptions.EMAIL_LINK_SIGN_IN_SKIP_NATIVE_AUTH);
            return;
        }

        String email = options.getEmail();
        String emailLink = options.getEmailLink();

        getFirebaseAuthInstance()
            .signInWithEmailLink(email, emailLink)
            .addOnCompleteListener(plugin.getActivity(), task -> {
                if (task.isSuccessful()) {
                    AuthResult authResult = task.getResult();
                    SignInResult result = new SignInResult(authResult);
                    callback.success(result);
                } else {
                    Exception exception = task.getException();
                    callback.error(exception);
                }
            });
    }

    public void signInWithFacebook(final PluginCall call) {
        if (facebookAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Facebook"));
            return;
        }
        facebookAuthProviderHandler.signIn(call);
    }

    public void signInWithGithub(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.GITHUB);
    }

    public void signInWithGoogle(final PluginCall call) {
        if (googleAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Google"));
            return;
        }
        googleAuthProviderHandler.signIn(call);
    }

    public void signInWithMicrosoft(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.MICROSOFT);
    }

    public void signInWithOpenIdConnect(final PluginCall call, final String providerId) {
        oAuthProviderHandler.signIn(call, providerId);
    }

    public void signInWithPhoneNumber(final SignInWithPhoneNumberOptions options) throws Exception {
        if (phoneAuthProviderHandler == null) {
            throw new Exception(createProviderNotEnabledErrorMessage("Phone"));
        }
        phoneAuthProviderHandler.signIn(options);
    }

    public void signInWithPlayGames(final PluginCall call) {
        if (playGamesAuthProviderHandler == null) {
            call.reject(createProviderNotEnabledErrorMessage("Play Games"));
            return;
        }
        playGamesAuthProviderHandler.signIn(call);
    }

    public void signInWithTwitter(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.TWITTER);
    }

    public void signInWithYahoo(final PluginCall call) {
        oAuthProviderHandler.signIn(call, ProviderId.YAHOO);
    }

    public void signOut(@NonNull EmptyResultCallback callback) {
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
        callback.success();
    }

    public void startActivityForResult(final PluginCall call, Intent intent, String callbackName) {
        plugin.startActivityForResult(call, intent, callbackName);
    }

    public void unlink(@NonNull UnlinkOptions options, @NonNull NonEmptyResultCallback<UnlinkResult> callback) {
        String providerId = options.getProviderId();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        user.unlink(providerId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AuthResult authResult = task.getResult();
                UnlinkResult result = new UnlinkResult(authResult.getUser());
                callback.success(result);
            } else {
                Exception exception = task.getException();
                callback.error(exception);
            }
        });
    }

    public void updateEmail(@NonNull UpdateEmailOptions options, @NonNull EmptyResultCallback callback) {
        String newEmail = options.getNewEmail();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        user.updateEmail(newEmail)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void updatePassword(@NonNull UpdatePasswordOptions options, @NonNull EmptyResultCallback callback) {
        String newPassword = options.getNewPassword();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        user.updatePassword(newPassword)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void updateProfile(@NonNull UpdateProfileOptions options, @NonNull EmptyResultCallback callback) {
        String displayName = options.getDisplayName();
        String photoUrl = options.getPhotoUrl();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        UserProfileChangeRequest.Builder profileUpdates = new UserProfileChangeRequest.Builder();

        if (displayName != null) {
            profileUpdates.setDisplayName(displayName);
        }
        if (photoUrl != null) {
            profileUpdates.setPhotoUri(Uri.parse(photoUrl));
        }

        user.updateProfile(profileUpdates.build())
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void useAppLanguage(@NonNull EmptyResultCallback callback) {
        getFirebaseAuthInstance().useAppLanguage();
        callback.success();
    }

    public void useEmulator(@NonNull UseEmulatorOptions options, @NonNull EmptyResultCallback callback) {
        String host = options.getHost();
        int port = options.getPort();

        getFirebaseAuthInstance().useEmulator(host, port);
        callback.success();
    }

    public void verifyBeforeUpdateEmail(@NonNull VerifyBeforeUpdateEmailOptions options, @NonNull EmptyResultCallback callback)
        throws Exception {
        String newEmail = options.getNewEmail();

        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }

        ActionCodeSettings actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.getActionCodeSettings());

        Task<Void> task;
        if (actionCodeSettings == null) {
            task = user.verifyBeforeUpdateEmail(newEmail);
        } else {
            task = user.verifyBeforeUpdateEmail(newEmail, actionCodeSettings);
        }
        task.addOnSuccessListener(unused -> callback.success()).addOnFailureListener(exception -> callback.error(exception));
    }

    @NonNull
    private static String createProviderNotEnabledErrorMessage(@NonNull String providerName) {
        return (
            providerName +
            " sign-in provider is not enabled. Make sure to add the provider to the 'providers' list in the Capacitor configuration."
        );
    }

    private void getIdToken(boolean forceRefresh, @NonNull final NonEmptyResultCallback<GetIdTokenResult> callback) {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        if (user == null) {
            callback.error(CustomExceptions.NO_USER_SIGNED_IN);
            return;
        }
        Task<GetTokenResult> tokenResultTask = user.getIdToken(forceRefresh);
        tokenResultTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult().getToken();
                GetIdTokenResult result = new GetIdTokenResult(token);
                callback.success(result);
            } else {
                Exception exception = task.getException();
                callback.error(exception);
            }
        });
    }

    private void handleAuthStateChange() {
        FirebaseUser user = getFirebaseAuthInstance().getCurrentUser();
        AuthStateChangeEvent event = new AuthStateChangeEvent(user);
        plugin.notifyAuthStateChangeListeners(event);
    }

    private void handleIdTokenChange() {
        NonEmptyResultCallback<GetIdTokenResult> callback = new NonEmptyResultCallback<>() {
            @Override
            public void success(@NonNull GetIdTokenResult result) {
                IdTokenChangeEvent event = new IdTokenChangeEvent(result.getToken());
                plugin.notifyIdTokenChangeListeners(event);
            }

            @Override
            public void error(@NonNull Exception exception) {
                Logger.error(TAG, exception.getMessage(), exception);
            }
        };
        getIdToken(false, callback);
    }

    private void initAuthProviderHandlers(FirebaseAuthenticationConfig config) {
        List<String> providerList = Arrays.asList(config.getProviders());
        if (providerList.contains(ProviderId.APPLE)) {
            appleAuthProviderHandler = new AppleAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.FACEBOOK)) {
            facebookAuthProviderHandler = new FacebookAuthProviderHandler(this);
        }
        if (providerList.contains(ProviderId.GOOGLE)) {
            googleAuthProviderHandler = new GoogleAuthProviderHandler(this);
            googleAuthorizationResultLauncher = getPlugin()
                .getActivity()
                .registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result ->
                    googleAuthProviderHandler.handleActivityResult(result)
                );
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
