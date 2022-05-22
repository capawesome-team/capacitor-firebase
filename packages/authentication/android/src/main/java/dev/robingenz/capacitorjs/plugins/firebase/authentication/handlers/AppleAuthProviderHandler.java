package dev.robingenz.capacitorjs.plugins.firebase.authentication.handlers;

import android.util.Log;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.OAuthProvider;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.FirebaseAuthentication;
import dev.robingenz.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationPlugin;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AppleAuthProviderHandler {

    private final FirebaseAuthentication pluginImplementation;
    private String currentNonce;

    public AppleAuthProviderHandler(FirebaseAuthentication pluginImplementation) {
        this.pluginImplementation = pluginImplementation;
    }

    // From https://firebase.google.com/docs/auth/android/apple#advanced_handle_the_sign-in_flow_manually
    private String generateNonce(int length) {
        SecureRandom generator = new SecureRandom();

        CharsetDecoder charsetDecoder = StandardCharsets.US_ASCII.newDecoder();
        charsetDecoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
        charsetDecoder.onMalformedInput(CodingErrorAction.IGNORE);

        byte[] bytes = new byte[length];
        ByteBuffer inBuffer = ByteBuffer.wrap(bytes);
        CharBuffer outBuffer = CharBuffer.allocate(length);
        while (outBuffer.hasRemaining()) {
            generator.nextBytes(bytes);
            inBuffer.rewind();
            charsetDecoder.reset();
            charsetDecoder.decode(inBuffer, outBuffer, false);
        }
        outBuffer.flip();
        return outBuffer.toString();
    }

    private String sha256(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(s.getBytes());
        StringBuilder hash = new StringBuilder();
        for (byte c : digest) {
            hash.append(String.format("%02x", c));
        }
        return hash.toString();
    }

    public void signIn(PluginCall call) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("apple.com");
        applySignInOptions(call, provider);
        Task<AuthResult> pendingResultTask = pluginImplementation.getFirebaseAuthInstance().getPendingAuthResult();
        if (pendingResultTask == null) {
            currentNonce = generateNonce(32);
            try {
                provider.addCustomParameter("nonce", sha256(currentNonce));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            startActivityForSignIn(call, provider);
        } else {
            finishActivityForSignIn(call, pendingResultTask);
        }
    }

    private void startActivityForSignIn(final PluginCall call, OAuthProvider.Builder provider) {
        pluginImplementation
            .getFirebaseAuthInstance()
            .startActivityForSignInWithProvider(pluginImplementation.getPlugin().getActivity(), provider.build())
            .addOnSuccessListener(
                authResult -> {
                    AuthCredential credential = authResult.getCredential();
                    pluginImplementation.handleSuccessfulSignIn(call, credential, null, currentNonce, null);
                }
            )
            .addOnFailureListener(exception -> pluginImplementation.handleFailedSignIn(call, null, exception));
    }

    private void finishActivityForSignIn(final PluginCall call, Task<AuthResult> pendingResultTask) {
        pendingResultTask
            .addOnSuccessListener(
                authResult -> {
                    AuthCredential credential = authResult.getCredential();
                    pluginImplementation.handleSuccessfulSignIn(call, credential, null, currentNonce, null);
                }
            )
            .addOnFailureListener(exception -> pluginImplementation.handleFailedSignIn(call, null, exception));
    }

    private void applySignInOptions(PluginCall call, OAuthProvider.Builder provider) {
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
