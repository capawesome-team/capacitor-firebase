package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.getcapacitor.JSArray;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.util.List;
import org.json.JSONException;

public class FacebookAuthProviderHandler {

    public static final int RC_FACEBOOK_AUTH = 0xface;
    public static final String ERROR_SIGN_IN_CANCELED = "Sign in canceled.";
    public static final String ERROR_LINK_CANCELED = "Link canceled.";
    private FirebaseAuthentication pluginImplementation;
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;

    @Nullable
    private PluginCall savedCall;

    @Nullable
    private Boolean isLink;

    public FacebookAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
        try {
            mCallbackManager = CallbackManager.Factory.create();
            loginButton = new LoginButton(pluginImplementation.getPlugin().getContext());

            loginButton.setPermissions("email", "public_profile");
            loginButton.registerCallback(
                mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleSuccessCallback(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        handleCancelCallback();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        handleErrorCallback(exception);
                    }
                }
            );
        } catch (Exception exception) {
            Log.e(FirebaseAuthenticationPlugin.TAG, "initialization failed.", exception);
        }
    }

    public void signIn(PluginCall call) {
        this.savedCall = call;
        this.isLink = false;
        this.applySignInOptions(call, this.loginButton);
        this.loginButton.performClick();
    }

    public void link(PluginCall call) {
        this.savedCall = call;
        this.isLink = true;
        this.applySignInOptions(call, this.loginButton);
        this.loginButton.performClick();
    }

    public void signOut() {
        LoginManager.getInstance().logOut();
    }

    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void applySignInOptions(final PluginCall call, LoginButton button) {
        JSArray scopes = call.getArray("scopes");
        if (scopes != null) {
            try {
                List<String> scopeList = scopes.toList();
                scopeList.add("email");
                scopeList.add("public_profile");
                button.setPermissions(scopeList);
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "applySignInOptions failed.", exception);
            }
        }
    }

    private void handleSuccessCallback(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        String accessTokenString = accessToken.getToken();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessTokenString);
        if (savedCall == null || isLink == null) {
            return;
        }
        if (isLink) {
            pluginImplementation.handleSuccessfulLink(savedCall, credential, null, null, accessTokenString);
        } else {
            pluginImplementation.handleSuccessfulSignIn(savedCall, credential, null, null, accessTokenString, null);
        }
    }

    private void handleCancelCallback() {
        if (savedCall == null || isLink == null) {
            return;
        }
        if (isLink) {
            pluginImplementation.handleFailedLink(savedCall, ERROR_LINK_CANCELED, null);
        } else {
            pluginImplementation.handleFailedSignIn(savedCall, ERROR_SIGN_IN_CANCELED, null);
        }
    }

    private void handleErrorCallback(FacebookException exception) {
        if (savedCall == null || isLink == null) {
            return;
        }
        if (isLink) {
            pluginImplementation.handleFailedLink(savedCall, null, exception);
        } else {
            pluginImplementation.handleFailedSignIn(savedCall, null, exception);
        }
    }
}
