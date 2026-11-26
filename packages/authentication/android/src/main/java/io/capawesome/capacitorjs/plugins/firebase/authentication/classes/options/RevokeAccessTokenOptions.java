package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class RevokeAccessTokenOptions {

    @NonNull
    private final String token;

    public RevokeAccessTokenOptions(@NonNull PluginCall call) throws Exception {
        this.token = RevokeAccessTokenOptions.getTokenFromCall(call);
    }

    @NonNull
    public String getToken() {
        return this.token;
    }

    @NonNull
    private static String getTokenFromCall(@NonNull PluginCall call) throws Exception {
        String token = call.getString("token");
        if (token == null) {
            throw CustomExceptions.TOKEN_MISSING;
        }
        return token;
    }
}
