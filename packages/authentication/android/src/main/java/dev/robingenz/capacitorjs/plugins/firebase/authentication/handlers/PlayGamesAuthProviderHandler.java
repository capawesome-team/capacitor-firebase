package dev.robingenz.capacitorjs.plugins.firebase.authentication.handlers;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
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
import dev.robingenz.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.R;
import java.util.List;
import org.json.JSONException;

public class PlayGamesAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;
    private GoogleSignInClient mGoogleSignInClient;

    public PlayGamesAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestServerAuthCode(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .build();
        mGoogleSignInClient = GoogleSignIn.getClient(pluginImplementation.getPlugin().getActivity(), gso);
    }

    public void signIn(PluginCall call) {
        JSArray scopes = call.getArray("scopes");
        // Got scopes? Need to get the client again with these new options.
        if (scopes != null) {
            try {
                List<String> scopeList = scopes.toList();
                GoogleSignInOptions.Builder gsob = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
                    .requestEmail();

                for (String scope : scopeList) {
                    gsob = gsob.requestScopes(new Scope(scope));
                }

                mGoogleSignInClient = GoogleSignIn.getClient(pluginImplementation.getPlugin().getActivity(), gsob.build());
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "signIn (applying scopes) failed.", exception);
            }
        }

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        pluginImplementation.startActivityForResult(call, signInIntent, "handlePlayGamesAuthProviderActivityResult");
    }

    public void signOut() {
        mGoogleSignInClient.signOut();
    }

    public void handleOnActivityResult(PluginCall call, ActivityResult result) {
        Intent data = result.getData();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String serverAuthCode = account.getServerAuthCode();
            AuthCredential credential = PlayGamesAuthProvider.getCredential(serverAuthCode);
            String idToken = account.getIdToken();
            pluginImplementation.handleSuccessfulSignIn(call, credential, idToken);
        } catch (ApiException exception) {
            pluginImplementation.handleFailedSignIn(call, null, exception);
        }
    }
}
