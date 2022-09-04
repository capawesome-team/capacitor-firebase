package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.util.Log;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.OAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class OAuthProviderHandler {

    private final FirebaseAuthentication pluginImplementation;

    public OAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void link(final PluginCall call, String providerId) {
        if (pluginImplementation.getCurrentUser() == null) {
            call.reject(FirebaseAuthenticationPlugin.ERROR_NO_USER_SIGNED_IN);
            return;
        }
        dispatch(call, providerId, true);
    }

    public void signIn(final PluginCall call, String providerId) {
        dispatch(call, providerId, false);
    }

    private void dispatch(final PluginCall call, String providerId, final Boolean isLink) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder(providerId);
        applySignInOptions(call, provider);
        Task<AuthResult> pendingResultTask = pluginImplementation.getFirebaseAuthInstance().getPendingAuthResult();
        if (pendingResultTask == null) {
            if (isLink) startActivityForLink(call, provider); else startActivityForSignIn(call, provider);
        } else {
            if (isLink) finishActivityForLink(call, pendingResultTask); else finishActivityForSignIn(call, pendingResultTask);
        }
    }

    private void startActivityForLink(final PluginCall call, OAuthProvider.Builder provider) {
        pluginImplementation
            .getCurrentUser()
            .startActivityForLinkWithProvider(pluginImplementation.getPlugin().getActivity(), provider.build())
            .addOnSuccessListener(authResult -> pluginImplementation.handleSuccessfulLink(call, authResult, null, null, null))
            .addOnFailureListener(exception -> pluginImplementation.handleFailedLink(call, null, exception));
    }

    private void finishActivityForLink(final PluginCall call, Task<AuthResult> pendingResultTask) {
        pendingResultTask
            .addOnSuccessListener(authResult -> pluginImplementation.handleSuccessfulLink(call, authResult, null, null, null))
            .addOnFailureListener(exception -> pluginImplementation.handleFailedLink(call, null, exception));
    }

    private void startActivityForSignIn(final PluginCall call, OAuthProvider.Builder provider) {
        pluginImplementation
            .getFirebaseAuthInstance()
            .startActivityForSignInWithProvider(pluginImplementation.getPlugin().getActivity(), provider.build())
            .addOnSuccessListener(authResult -> pluginImplementation.handleSuccessfulSignIn(call, authResult, null, null, null))
            .addOnFailureListener(exception -> pluginImplementation.handleFailedSignIn(call, null, exception));
    }

    private void finishActivityForSignIn(final PluginCall call, Task<AuthResult> pendingResultTask) {
        pendingResultTask
            .addOnSuccessListener(authResult -> pluginImplementation.handleSuccessfulSignIn(call, authResult, null, null, null))
            .addOnFailureListener(exception -> pluginImplementation.handleFailedSignIn(call, null, exception));
    }

    private void applySignInOptions(final PluginCall call, OAuthProvider.Builder provider) {
        JSArray customParameters = call.getArray("customParameters");
        if (customParameters != null) {
            try {
                List<Object> customParametersList = customParameters.toList();
                for (int i = 0; i < customParametersList.size(); i++) {
                    JSObject customParameter = JSObject.fromJSONObject((JSONObject) customParametersList.get(i));
                    String key = customParameter.getString("key");
                    String value = customParameter.getString("value");
                    if (key == null || value == null) {
                        continue;
                    }
                    provider.addCustomParameter(key, value);
                }
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "applySignInOptions failed.", exception);
            }
        }

        JSArray scopes = call.getArray("scopes");
        if (scopes != null) {
            try {
                List<String> scopeList = scopes.toList();
                provider.setScopes(scopeList);
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "applySignInOptions failed.", exception);
            }
        }
    }
}
