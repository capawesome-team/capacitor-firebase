package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class RevokeAccessTokenOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String token;

    public RevokeAccessTokenOptions(@NonNull PluginCall call) throws Exception {
        this.token = RevokeAccessTokenOptions.getTokenFromCall(call);
        this.appName = RevokeAccessTokenOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getToken() {
        return this.token;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
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
