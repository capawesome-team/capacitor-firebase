package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResult;
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
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import io.capawesome.capacitorjs.plugins.firebase.authentication.R;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.NonEmptyCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONException;

public class GoogleAuthProviderHandler {

    private FirebaseAuthentication pluginImplementation;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    private AuthCredential lastAuthCredential;

    @Nullable
    private PluginCall lastCall;

    @Nullable
    private String lastIdToken;

    private boolean wasLink = false;

    public GoogleAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
        this.mGoogleSignInClient = buildGoogleSignInClient();
    }

    public void handleActivityResult(@NonNull ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            try {
                AuthorizationResult authorizationResult = Identity.getAuthorizationClient(
                    pluginImplementation.getPlugin().getActivity()
                ).getAuthorizationResultFromIntent(intent);
                handleAuthorizationResult(authorizationResult);
            } catch (ApiException exception) {
                handleAuthorizationResultError(exception);
            }
        } else {
            handleAuthorizationResultError(new Exception("Authorization canceled."));
        }
    }

    public void handleOnActivityResult(@NonNull final PluginCall call, @NonNull ActivityResult result, boolean isLink) {
        Intent data = result.getData();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String idToken = account.getIdToken();
            String serverAuthCode = account.getServerAuthCode();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            new Thread(() -> {
                String accessToken = null;
                List<String> scopes = new ArrayList<>();
                scopes.add("oauth2:email");
                scopes.addAll(getScopesAsList(call));

                try {
                    accessToken = GoogleAuthUtil.getToken(
                        mGoogleSignInClient.getApplicationContext(),
                        account.getAccount(),
                        String.join(" ", scopes)
                    );
                    // Clears local cache after every login attempt
                    // to ensure permissions changes elsewhere are reflected in future tokens
                    GoogleAuthUtil.clearToken(mGoogleSignInClient.getApplicationContext(), accessToken);
                } catch (IOException | GoogleAuthException exception) {
                    if (isLink) {
                        pluginImplementation.handleFailedLink(call, null, exception);
                    } else {
                        pluginImplementation.handleFailedSignIn(call, null, exception);
                    }
                    return;
                }
                if (isLink) {
                    pluginImplementation.handleSuccessfulLink(call, credential, idToken, null, accessToken, serverAuthCode);
                } else {
                    pluginImplementation.handleSuccessfulSignIn(call, credential, idToken, null, accessToken, serverAuthCode, null);
                }
            }).start();
        } catch (ApiException exception) {
            if (isLink) {
                pluginImplementation.handleFailedLink(call, null, exception);
            } else {
                pluginImplementation.handleFailedSignIn(call, null, exception);
            }
        }
    }

    public void handleAuthorizationResult(@NonNull AuthorizationResult authorizationResult) {
        if (lastCall == null) {
            return;
        }
        String accessToken = authorizationResult.getAccessToken();
        String serverAuthCode = authorizationResult.getServerAuthCode();
        if (wasLink) {
            pluginImplementation.handleSuccessfulLink(lastCall, lastAuthCredential, lastIdToken, null, accessToken, serverAuthCode);
        } else {
            pluginImplementation.handleSuccessfulSignIn(lastCall, lastAuthCredential, lastIdToken, null, accessToken, serverAuthCode, null);
        }
        lastAuthCredential = null;
        lastCall = null;
        lastIdToken = null;
    }

    public void handleAuthorizationResultError(@NonNull Exception exception) {
        if (lastCall == null) {
            return;
        }
        if (wasLink) {
            pluginImplementation.handleFailedLink(lastCall, null, exception);
        } else {
            pluginImplementation.handleFailedSignIn(lastCall, null, exception);
        }
        lastAuthCredential = null;
        lastCall = null;
        lastIdToken = null;
    }

    public void signIn(final PluginCall call) {
        Boolean useCredentialManagerRaw = call.getBoolean("useCredentialManager");
        boolean useCredentialManager = (useCredentialManagerRaw != null) ? useCredentialManagerRaw : true;

        if (useCredentialManager) {
            signInOrLink(call, false);
        } else {
            mGoogleSignInClient = buildGoogleSignInClient(call);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            pluginImplementation.startActivityForResult(call, signInIntent, "handleGoogleAuthProviderSignInActivityResult");
        }
    }

    public void link(final PluginCall call) {
        Boolean useCredentialManagerRaw = call.getBoolean("useCredentialManager");
        boolean useCredentialManager = (useCredentialManagerRaw != null) ? useCredentialManagerRaw : true;

        if (useCredentialManager) {
            signInOrLink(call, true);
        } else {
            mGoogleSignInClient = buildGoogleSignInClient(call);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            pluginImplementation.startActivityForResult(call, signInIntent, "handleGoogleAuthProviderLinkActivityResult");
        }
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        return buildGoogleSignInClient(null);
    }

    private GoogleSignInClient buildGoogleSignInClient(@Nullable PluginCall call) {
        GoogleSignInOptions.Builder googleSignInOptionsBuilder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .requestServerAuthCode(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id))
            .requestEmail();

        if (call != null) {
            List<String> scopeList = getScopesAsList(call);
            for (String scope : scopeList) {
                googleSignInOptionsBuilder = googleSignInOptionsBuilder.requestScopes(new Scope(scope));
            }
        }

        return GoogleSignIn.getClient(pluginImplementation.getPlugin().getActivity(), googleSignInOptionsBuilder.build());
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

    private List<String> getScopesAsList(@NonNull PluginCall call) {
        List<String> scopeList = new ArrayList<>();
        JSArray scopes = call.getArray("scopes");
        if (scopes != null) {
            try {
                scopeList = scopes.toList();
            } catch (JSONException exception) {
                Log.e(FirebaseAuthenticationPlugin.TAG, "getScopesAsList failed.", exception);
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
                wasLink = isLink;
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
     * @param callback  The callback to call with the result. This callback is NOT called if an intent is launched.
     */
    private void requestAuthorizationResult(@NonNull final List<Scope> scopes, @NonNull NonEmptyCallback<AuthorizationResult> callback) {
        AuthorizationRequest authorizationRequest = AuthorizationRequest.builder()
            .requestOfflineAccess(pluginImplementation.getPlugin().getContext().getString(R.string.default_web_client_id), true)
            .setRequestedScopes(scopes)
            .build();
        Identity.getAuthorizationClient(pluginImplementation.getPlugin().getContext())
            .authorize(authorizationRequest)
            .addOnSuccessListener(authorizationResult -> {
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
            })
            .addOnFailureListener(callback::error);
    }
}
