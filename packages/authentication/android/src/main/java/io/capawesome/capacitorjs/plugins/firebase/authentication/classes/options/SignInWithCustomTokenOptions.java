package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationConfig;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;

public class SignInWithCustomTokenOptions extends SignInOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String token;

    public SignInWithCustomTokenOptions(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config) {
        super(call, config);
        this.token = SignInWithCustomTokenOptions.getTokenFromCall(call);
        this.appName = SignInWithCustomTokenOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @NonNull
    private static String getTokenFromCall(@NonNull PluginCall call) {
        return call.getString("token", "");
    }
}
