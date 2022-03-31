package dev.robingenz.capacitorjs.plugins.firebase.authentication.handlers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.R;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class GoogleAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        mGoogleSignInClient = GoogleSignIn.getClient(pluginImplementation.getPlugin().getActivity(), gso);
    }

    public void signIn(PluginCall call) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        pluginImplementation.startActivityForResult(call, signInIntent, "handleGoogleAuthProviderActivityResult");
    }

    public void signOut() {
        mGoogleSignInClient.signOut();
    }

    public void handleOnActivityResult(PluginCall call, ActivityResult result) {
        Intent data = result.getData();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String idToken = account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            GoogleAccessTokenTaskParam param = new GoogleAccessTokenTaskParam(call, credential, idToken, account);
            getAccessTokenForAccountAsyncTask(param);
        } catch (ApiException exception) {
            pluginImplementation.handleFailedSignIn(call, null, exception);
        }
    }

    private void getAccessTokenForAccountAsyncTask(GoogleAccessTokenTaskParam param) {
        new GoogleAccessTokenTask(pluginImplementation).execute(param);
    }

    private static class GoogleAccessTokenTask extends AsyncTask<GoogleAccessTokenTaskParam, Void, GoogleAccessTokenTaskResult> {

        private WeakReference<FirebaseAuthentication> pluginImplementationReference;

        GoogleAccessTokenTask(FirebaseAuthentication pluginImplementation) {
            pluginImplementationReference = new WeakReference<>(pluginImplementation);
        }

        @Override
        protected GoogleAccessTokenTaskResult doInBackground(GoogleAccessTokenTaskParam... params) {
            return getAccessTokenForAccount(params[0]);
        }

        @Override
        protected void onPostExecute(GoogleAccessTokenTaskResult result) {
            FirebaseAuthentication pluginImplementation = pluginImplementationReference.get();
            pluginImplementation.handleSuccessfulSignIn(result.call, result.credential, result.idToken, null, result.accessToken);
        }

        private GoogleAccessTokenTaskResult getAccessTokenForAccount(GoogleAccessTokenTaskParam param) {
            FirebaseAuthentication pluginImplementation = pluginImplementationReference.get();
            String scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE;
            GoogleAccessTokenTaskResult result = new GoogleAccessTokenTaskResult(param.call, param.credential, param.idToken, null);
            try {
                result.accessToken =
                    GoogleAuthUtil.getToken(pluginImplementation.getPlugin().getContext(), param.account.getAccount(), scope, new Bundle());
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}

class GoogleAccessTokenTaskParam {

    public PluginCall call;
    public AuthCredential credential;
    public String idToken;
    public GoogleSignInAccount account;

    public GoogleAccessTokenTaskParam(PluginCall call, AuthCredential credential, String idToken, GoogleSignInAccount account) {
        this.call = call;
        this.credential = credential;
        this.idToken = idToken;
        this.account = account;
    }
}

class GoogleAccessTokenTaskResult {

    public PluginCall call;
    public AuthCredential credential;
    public String idToken;
    public String accessToken;

    public GoogleAccessTokenTaskResult(PluginCall call, AuthCredential credential, String idToken, @Nullable String accessToken) {
        this.call = call;
        this.credential = credential;
        this.idToken = idToken;
        this.accessToken = accessToken;
    }
}
