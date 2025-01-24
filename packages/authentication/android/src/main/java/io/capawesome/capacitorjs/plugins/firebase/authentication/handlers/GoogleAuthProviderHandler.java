package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;

import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.R;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.EmptyResultCallback;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GoogleAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;

    public GoogleAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void signIn(final PluginCall call) {
        signInOrLink(call, false);
    }

    public void link(final PluginCall call) {
        signInOrLink(call, true);
    }

    public void signOut() {
        ClearCredentialStateRequest request = new ClearCredentialStateRequest();
        Executor executor = Executors.newSingleThreadExecutor();
        CredentialManager credentialManager = CredentialManager.create(pluginImplementation.getPlugin().getActivity());
        credentialManager.clearCredentialStateAsync(
                request,
                null,
                executor,
                new CredentialManagerCallback<Void, ClearCredentialException>() {
                    @Override
                    public void onResult(Void result) {
                        // No-op
                    }

                    @Override
                    public void onError(@NonNull ClearCredentialException exception) {
                        // No-op
                    }
                }
        );
    }

    private void handleGetCredentialError(final PluginCall call, final boolean isLink, final GetCredentialException exception) {
        if (isLink) {
            pluginImplementation.handleFailedLink(call, null, exception);
        } else {
            pluginImplementation.handleFailedSignIn(call, null, exception);
        }
    }

    private void handleGetCredentialResult(final PluginCall call, final boolean isLink, final GetCredentialResponse response) {
        Credential credential = response.getCredential();
        if (credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            Bundle data = credential.getData();
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(data);
            String idToken = googleIdTokenCredential.getIdToken();
            AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
            if (isLink) {
                pluginImplementation.handleSuccessfulLink(call, authCredential, idToken, null, null, null);
            } else {
                pluginImplementation.handleSuccessfulSignIn(call, authCredential, idToken, null, null, null, null);
            }
        }
    }

    public void signInOrLink(final PluginCall call, final boolean isLink) {
        Executor executor = Executors.newSingleThreadExecutor();
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                // Your server's client ID, not your Android client ID
                .setServerClientId(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
                // Show all accounts on the device (not just the accounts that have been used previously)
                .setFilterByAuthorizedAccounts(false)
                .build();
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
        CredentialManager credentialManager = CredentialManager.create(pluginImplementation.getPlugin().getActivity());
        credentialManager.getCredentialAsync(
                pluginImplementation.getPlugin().getContext(),
                request,
                null,
                executor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse response) {
                        handleGetCredentialResult(call, isLink, response);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException exception) {
                        handleGetCredentialError(call, isLink, exception);
                    }
                }
        );
    }
}
