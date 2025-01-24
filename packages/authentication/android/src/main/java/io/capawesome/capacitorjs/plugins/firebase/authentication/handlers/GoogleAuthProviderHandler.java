package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import com.getcapacitor.JSArray;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.Scope;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import io.capawesome.capacitorjs.plugins.firebase.authentication.R;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.NonEmptyCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONException;

public class GoogleAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;
    private boolean isLastCallLink;

    @Nullable
    private AuthCredential lastAuthCredential;

    @Nullable
    private PluginCall lastCall;

    @Nullable
    private String lastIdToken;

    public GoogleAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    public void handleAuthorizationResult(@NonNull AuthorizationResult authorizationResult) {
        if (lastCall == null) {
            return;
        }
        String accessToken = authorizationResult.getAccessToken();
        String serverAuthCode = authorizationResult.getServerAuthCode();
        if (isLastCallLink) {
            pluginImplementation.handleSuccessfulLink(lastCall, lastAuthCredential, lastIdToken, null, accessToken, serverAuthCode);
        } else {
            pluginImplementation.handleSuccessfulSignIn(lastCall, lastAuthCredential, lastIdToken, null, accessToken, serverAuthCode, null);
        }
    }

    public void handleAuthorizationResultError(@NonNull Exception exception) {
        if (lastCall == null) {
            return;
        }
        if (isLastCallLink) {
            pluginImplementation.handleFailedLink(lastCall, null, exception);
        } else {
            pluginImplementation.handleFailedSignIn(lastCall, null, exception);
        }
    }

    public void link(final PluginCall call) {
        signInOrLink(call, true);
    }

    public void signIn(final PluginCall call) {
        signInOrLink(call, false);
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

    private List<Scope> buildScopeList(@NonNull PluginCall call) {
        List<Scope> scopeList = new ArrayList<>();
        JSArray scopes = call.getArray("scopes");
        if (scopes != null) {
            try {
                List<String> optionArrayElements = scopes.toList();
                for (String optionArrayElement : optionArrayElements) {
                    scopeList.add(new Scope(optionArrayElement));
                }
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "Error parsing scopes.", exception);
            }
        }
        return scopeList;
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
            List<Scope> scopes = buildScopeList(call);
            if (scopes.isEmpty()) {
                if (isLink) {
                    pluginImplementation.handleSuccessfulLink(call, authCredential, idToken, null, null, null);
                } else {
                    pluginImplementation.handleSuccessfulSignIn(call, authCredential, idToken, null, null, null, null);
                }
            } else {
                lastAuthCredential = authCredential;
                lastCall = call;
                lastIdToken = idToken;
                requestAuthorizationResult(
                    scopes,
                    new NonEmptyCallback<AuthorizationResult>() {
                        @Override
                        public void success(@NonNull AuthorizationResult result) {
                            if (isLink) {
                                pluginImplementation.handleSuccessfulLink(call, authCredential, idToken, null, null, null);
                            } else {
                                pluginImplementation.handleSuccessfulSignIn(call, authCredential, idToken, null, null, null, null);
                            }
                        }

                        @Override
                        public void error(Exception exception) {
                            if (isLink) {
                                pluginImplementation.handleFailedLink(call, null, exception);
                            } else {
                                pluginImplementation.handleFailedSignIn(call, null, exception);
                            }
                        }
                    }
                );
            }
        }
    }

    private void signInOrLink(final PluginCall call, final boolean isLink) {
        Executor executor = Executors.newSingleThreadExecutor();
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
            // Your server's client ID, not your Android client ID
            .setServerClientId(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            // Show all accounts on the device (not just the accounts that have been used previously)
            .setFilterByAuthorizedAccounts(false)
            .build();
        GetCredentialRequest request = new GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build();
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

    /**
     * Request access token and server auth code.
     *
     * @param scopes    The scopes to request.
     * @param callback The callback to call with the result. This callback is NOT called if an intent is launched.
     */
    private void requestAuthorizationResult(@NonNull final List<Scope> scopes, @NonNull NonEmptyCallback<AuthorizationResult> callback) {
        AuthorizationRequest authorizationRequest = AuthorizationRequest
            .builder()
            .requestOfflineAccess(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id), true)
            .setRequestedScopes(scopes)
            .build();
        Identity
            .getAuthorizationClient(pluginImplementation.getPlugin().getContext())
            .authorize(authorizationRequest)
            .addOnSuccessListener(
                authorizationResult -> {
                    if (authorizationResult.hasResolution()) {
                        // Access needs to be granted by the user
                        PendingIntent pendingIntent = authorizationResult.getPendingIntent();
                        if (pendingIntent == null) {
                            return;
                        }
                        IntentSender intentSender = pendingIntent.getIntentSender();
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(intentSender).build();
                        pluginImplementation.googleAuthorizationResultLauncher.launch(intentSenderRequest);
                    } else {
                        // Access already granted, continue with user action
                        callback.success(authorizationResult);
                    }
                }
            )
            .addOnFailureListener(callback::error);
    }
}
