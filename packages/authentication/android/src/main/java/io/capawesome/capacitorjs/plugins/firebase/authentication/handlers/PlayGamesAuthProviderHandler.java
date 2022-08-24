package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import static io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHandler.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.api.Auth;
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

    public void link(PluginCall call) {
        dispatch(call, AuthType.LINK);
    }

    public void signIn(PluginCall call) {
        dispatch(call, AuthType.SIGN_IN);
    }

    private void dispatch(PluginCall call, AuthType authType) {
        this.mGoogleSignInClient = buildGoogleSignInClient(call);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInIntent.putExtra("authType", authType);
        pluginImplementation.startActivityForResult(call, signInIntent, "handlePlayGamesAuthProviderActivityResult");
    }

    public void signOut() {
        mGoogleSignInClient.signOut();
    }

    public void handleOnActivityResult(final PluginCall call, ActivityResult result) {
        Intent data = result.getData();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            Bundle extras = data.getExtras();
            AuthType authType = AuthType.fromId(extras.getInt("authType"));
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String serverAuthCode = account.getServerAuthCode();
            AuthCredential credential = PlayGamesAuthProvider.getCredential(serverAuthCode);
            String idToken = account.getIdToken();
            success(call, authType, pluginImplementation, credential, idToken, null, null, null);
        } catch (ApiException exception) {
            failure(call, null, exception);
        }
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        return buildGoogleSignInClient(null);
    }

    private GoogleSignInClient buildGoogleSignInClient(@Nullable final PluginCall call) {
        GoogleSignInOptions.Builder gsob = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
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
