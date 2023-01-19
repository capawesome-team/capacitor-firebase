package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.PlayGamesAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import io.capawesome.capacitorjs.plugins.firebase.authentication.R;
import java.util.List;
import org.json.JSONException;

public class PlayGamesAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;
    private GoogleSignInClient mGoogleSignInClient;

    public PlayGamesAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
        this.mGoogleSignInClient = buildGoogleSignInClient();
    }

    public void signIn(PluginCall call) {
        this.mGoogleSignInClient = buildGoogleSignInClient(call);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        pluginImplementation.startActivityForResult(call, signInIntent, "handlePlayGamesAuthProviderSignInActivityResult");
    }

    public void link(PluginCall call) {
        this.mGoogleSignInClient = buildGoogleSignInClient(call);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        pluginImplementation.startActivityForResult(call, signInIntent, "handlePlayGamesAuthProviderLinkActivityResult");
    }

    public void signOut() {
        mGoogleSignInClient.signOut();
    }

    public void handleOnActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result, boolean isLink) {
        Intent data = result.getData();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String serverAuthCode = account.getServerAuthCode();
            AuthCredential credential = PlayGamesAuthProvider.getCredential(serverAuthCode);
            String idToken = account.getIdToken();
            if (isLink) {
                pluginImplementation.handleSuccessfulLink(call, credential, idToken, null, null);
            } else {
                pluginImplementation.handleSuccessfulSignIn(call, credential, idToken, null, null, null);
            }
        } catch (ApiException exception) {
            if (isLink) {
                pluginImplementation.handleFailedLink(call, null, exception);
            } else {
                pluginImplementation.handleFailedSignIn(call, null, exception);
            }
        }
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        return buildGoogleSignInClient(null);
    }

    private GoogleSignInClient buildGoogleSignInClient(@Nullable final PluginCall call) {
        GoogleSignInOptions.Builder gsob = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .requestServerAuthCode(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .requestEmail();

        if (call != null) {
            JSArray scopes = call.getArray("scopes");
            if (scopes != null) {
                try {
                    List<String> scopeList = scopes.toList();
                    for (String scope : scopeList) {
                        gsob = gsob.requestScopes(new Scope(scope));
                    }
                } catch (JSONException exception) {
                    Log.e(FirebaseAuthenticationPlugin.TAG, "buildGoogleSignInClient failed.", exception);
                }
            }
        }

        return GoogleSignIn.getClient(pluginImplementation.getPlugin().getActivity(), gsob.build());
    }
}
