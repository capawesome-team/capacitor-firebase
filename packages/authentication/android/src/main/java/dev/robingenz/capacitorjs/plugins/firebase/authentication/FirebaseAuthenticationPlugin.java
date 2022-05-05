package dev.robingenz.capacitorjs.plugins.firebase.authentication;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.auth.FirebaseUser;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.handlers.FacebookAuthProviderHandler;

@CapacitorPlugin(name = "FirebaseAuthentication", requestCodes = { FacebookAuthProviderHandler.RC_FACEBOOK_AUTH })
public class FirebaseAuthenticationPlugin extends Plugin {

    public static final String TAG = "FirebaseAuthentication";
    public static final String ERROR_NO_USER_SIGNED_IN = "No user is signed in.";
    public static final String ERROR_OOB_CODE_MISSING = "oobCode must be provided.";
    public static final String ERROR_EMAIL_MISSING = "email must be provided.";
    public static final String ERROR_NEW_EMAIL_MISSING = "newEmail must be provided.";
    public static final String ERROR_PASSWORD_MISSING = "password must be provided.";
    public static final String ERROR_NEW_PASSWORD_MISSING = "newPassword must be provided.";
    public static final String ERROR_PHONE_NUMBER_SMS_CODE_MISSING = "phoneNumber or verificationId and verificationCode must be provided.";
    public static final String ERROR_HOST_MISSING = "host must be provided.";
    public static final String ERROR_SIGN_IN_FAILED = "signIn failed.";
    public static final String ERROR_CREATE_USER_WITH_EMAIL_AND_PASSWORD_FAILED = "createUserWithEmailAndPassword failed.";
    public static final String ERROR_CUSTOM_TOKEN_SKIP_NATIVE_AUTH =
        "signInWithCustomToken cannot be used in combination with skipNativeAuth.";
    public static final String ERROR_EMAIL_SIGN_IN_SKIP_NATIVE_AUTH =
        "createUserWithEmailAndPassword and signInWithEmailAndPassword cannot be used in combination with skipNativeAuth.";
    public static final String AUTH_STATE_CHANGE_EVENT = "authStateChange";
    private FirebaseAuthenticationConfig config;
    private FirebaseAuthentication implementation;

    public void load() {
        config = getFirebaseAuthenticationConfig();
        implementation = new FirebaseAuthentication(this, config);
        implementation.setAuthStateChangeListener(this::updateAuthState);
    }

    @PluginMethod
    public void applyActionCode(PluginCall call) {
        String oobCode = call.getString("oobCode");
        if (oobCode == null) {
            call.reject(ERROR_OOB_CODE_MISSING);
            return;
        }
        implementation.applyActionCode(oobCode, () -> call.resolve());
    }

    @PluginMethod
    public void createUserWithEmailAndPassword(PluginCall call) {
        implementation.createUserWithEmailAndPassword(call);
    }

    @PluginMethod
    public void confirmPasswordReset(PluginCall call) {
        String oobCode = call.getString("oobCode");
        if (oobCode == null) {
            call.reject(ERROR_OOB_CODE_MISSING);
            return;
        }
        String newPassword = call.getString("newPassword");
        if (newPassword == null) {
            call.reject(ERROR_NEW_PASSWORD_MISSING);
            return;
        }
        implementation.confirmPasswordReset(oobCode, newPassword, () -> call.resolve());
    }

    @PluginMethod
    public void getCurrentUser(PluginCall call) {
        FirebaseUser user = implementation.getCurrentUser();
        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(user);
        JSObject result = new JSObject();
        result.put("user", userResult);
        call.resolve(result);
    }

    @PluginMethod
    public void getIdToken(PluginCall call) {
        Boolean forceRefresh = call.getBoolean("forceRefresh", false);

        implementation.getIdToken(
            forceRefresh,
            new GetIdTokenResultCallback() {
                @Override
                public void success(String token) {
                    JSObject result = new JSObject();
                    result.put("token", token);
                    call.resolve(result);
                }

                @Override
                public void error(String message) {
                    call.reject(message);
                }
            }
        );
    }

    @PluginMethod
    public void sendEmailVerification(PluginCall call) {
        FirebaseUser user = implementation.getCurrentUser();
        if (user == null) {
            call.reject(ERROR_NO_USER_SIGNED_IN);
            return;
        }
        implementation.sendEmailVerification(user, () -> call.resolve());
    }

    @PluginMethod
    public void sendPasswordResetEmail(PluginCall call) {
        String email = call.getString("email");
        if (email == null) {
            call.reject(ERROR_EMAIL_MISSING);
            return;
        }
        implementation.sendPasswordResetEmail(email, () -> call.resolve());
    }

    @PluginMethod
    public void setLanguageCode(PluginCall call) {
        String languageCode = call.getString("languageCode", "");

        implementation.setLanguageCode(languageCode);
        call.resolve();
    }

    @PluginMethod
    public void signInWithApple(PluginCall call) {
        implementation.signInWithApple(call);
    }

    @PluginMethod
    public void signInWithCustomToken(PluginCall call) {
        implementation.signInWithCustomToken(call);
    }

    @PluginMethod
    public void signInWithEmailAndPassword(PluginCall call) {
        implementation.signInWithEmailAndPassword(call);
    }

    @PluginMethod
    public void signInWithFacebook(PluginCall call) {
        implementation.signInWithFacebook(call);
    }

    @PluginMethod
    public void signInWithGithub(PluginCall call) {
        implementation.signInWithGithub(call);
    }

    @PluginMethod
    public void signInWithGoogle(PluginCall call) {
        implementation.signInWithGoogle(call);
    }

    @PluginMethod
    public void signInWithMicrosoft(PluginCall call) {
        implementation.signInWithMicrosoft(call);
    }

    @PluginMethod
    public void signInWithPhoneNumber(PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        String verificationId = call.getString("verificationId");
        String verificationCode = call.getString("verificationCode");

        if (phoneNumber == null && (verificationId == null || verificationCode == null)) {
            call.reject(ERROR_PHONE_NUMBER_SMS_CODE_MISSING);
            return;
        }

        implementation.signInWithPhoneNumber(call);
    }

    @PluginMethod
    public void signInWithPlayGames(PluginCall call) {
        implementation.signInWithPlayGames(call);
    }

    @PluginMethod
    public void signInWithTwitter(PluginCall call) {
        implementation.signInWithTwitter(call);
    }

    @PluginMethod
    public void signInWithYahoo(PluginCall call) {
        implementation.signInWithYahoo(call);
    }

    @PluginMethod
    public void signOut(PluginCall call) {
        implementation.signOut(call);
    }

    @PluginMethod
    public void updateEmail(PluginCall call) {
        String newEmail = call.getString("newEmail");
        if (newEmail == null) {
            call.reject(ERROR_NEW_EMAIL_MISSING);
            return;
        }
        FirebaseUser user = implementation.getCurrentUser();
        if (user == null) {
            call.reject(ERROR_NO_USER_SIGNED_IN);
            return;
        }
        implementation.updateEmail(user, newEmail, () -> call.resolve());
    }

    @PluginMethod
    public void updatePassword(PluginCall call) {
        String newPassword = call.getString("newPassword");
        if (newPassword == null) {
            call.reject(ERROR_NEW_PASSWORD_MISSING);
            return;
        }
        FirebaseUser user = implementation.getCurrentUser();
        if (user == null) {
            call.reject(ERROR_NO_USER_SIGNED_IN);
            return;
        }
        implementation.updatePassword(user, newPassword, () -> call.resolve());
    }

    @PluginMethod
    public void useAppLanguage(PluginCall call) {
        implementation.useAppLanguage();
        call.resolve();
    }

    @PluginMethod
    public void useEmulator(PluginCall call) {
        String host = call.getString("host");
        if (host == null) {
            call.reject(ERROR_HOST_MISSING);
            return;
        }
        int port = call.getInt("port", 9099);

        implementation.useEmulator(host, port);
        call.resolve();
    }

    @Override
    public void startActivityForResult(PluginCall call, Intent intent, String callbackName) {
        super.startActivityForResult(call, intent, callbackName);
    }

    public void notifyListeners(String eventName, JSObject data) {
        super.notifyListeners(eventName, data);
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        implementation.handleOnActivityResult(requestCode, resultCode, data);
    }

    private void updateAuthState() {
        FirebaseUser user = implementation.getCurrentUser();
        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(user);
        JSObject result = new JSObject();
        result.put("user", userResult);
        notifyListeners(AUTH_STATE_CHANGE_EVENT, result);
    }

    @ActivityCallback
    private void handleGoogleAuthProviderActivityResult(PluginCall call, ActivityResult result) {
        implementation.handleGoogleAuthProviderActivityResult(call, result);
    }

    @ActivityCallback
    private void handlePlayGamesAuthProviderActivityResult(PluginCall call, ActivityResult result) {
        implementation.handlePlayGamesAuthProviderActivityResult(call, result);
    }

    private FirebaseAuthenticationConfig getFirebaseAuthenticationConfig() {
        FirebaseAuthenticationConfig config = new FirebaseAuthenticationConfig();

        boolean skipNativeAuth = getConfig().getBoolean("skipNativeAuth", config.getSkipNativeAuth());
        config.setSkipNativeAuth(skipNativeAuth);
        String[] providers = getConfig().getArray("providers", config.getProviders());
        config.setProviders(providers);

        return config;
    }
}
