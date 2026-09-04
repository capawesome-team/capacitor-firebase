package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class FacebookAuthProviderHandler {

    public static final String ERROR_SIGN_IN_CANCELED = "Sign in canceled.";
    public static final String ERROR_LINK_CANCELED = "Link canceled.";

    private final FirebaseAuthentication pluginImplementation;
    private final CallbackManager callbackManager = CallbackManager.Factory.create();

    @Nullable
    private LoginManager loginManager;

    @Nullable
    private PluginCall savedCall;

    private boolean isLink;

    public FacebookAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void signIn(PluginCall call) {
        logIn(call, false);
    }

    public void link(PluginCall call) {
        logIn(call, true);
    }

    public void signOut() {
        LoginManager.getInstance().logOut();
    }

    @NonNull
    private LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
            loginManager.registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(@NonNull LoginResult loginResult) {
                        handleSuccessCallback(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        handleCancelCallback();
                    }

                    @Override
                    public void onError(@NonNull FacebookException exception) {
                        handleErrorCallback(exception);
                    }
                }
            );
        }
        return loginManager;
    }

    @NonNull
    private List<String> getPermissions(PluginCall call) {
        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");
        JSArray scopes = call.getArray("scopes");
        if (scopes != null) {
            try {
                permissions.addAll(scopes.toList());
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "getPermissions failed.", exception);
            }
        }
        return permissions;
    }

    private void handleCancelCallback() {
        PluginCall call = takeSavedCall();
        if (call == null) {
            return;
        }
        if (isLink) {
            pluginImplementation.handleFailedLink(call, ERROR_LINK_CANCELED, null);
        } else {
            pluginImplementation.handleFailedSignIn(call, ERROR_SIGN_IN_CANCELED, null);
        }
    }

    private void handleErrorCallback(FacebookException exception) {
        PluginCall call = takeSavedCall();
        if (call == null) {
            return;
        }
        if (isLink) {
            pluginImplementation.handleFailedLink(call, null, exception);
        } else {
            pluginImplementation.handleFailedSignIn(call, null, exception);
        }
    }

    private void handleSuccessCallback(LoginResult loginResult) {
        PluginCall call = takeSavedCall();
        if (call == null) {
            return;
        }
        String accessToken = loginResult.getAccessToken().getToken();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken);
        if (isLink) {
            pluginImplementation.handleSuccessfulLink(call, credential, null, null, accessToken, null);
        } else {
            pluginImplementation.handleSuccessfulSignIn(call, credential, null, null, accessToken, null, null);
        }
    }

    private void logIn(PluginCall call, boolean isLink) {
        this.savedCall = call;
        this.isLink = isLink;
        getLoginManager().logIn(pluginImplementation.getPlugin().getActivity(), callbackManager, getPermissions(call), null);
    }

    @Nullable
    private PluginCall takeSavedCall() {
        PluginCall call = savedCall;
        savedCall = null;
        return call;
    }
}
