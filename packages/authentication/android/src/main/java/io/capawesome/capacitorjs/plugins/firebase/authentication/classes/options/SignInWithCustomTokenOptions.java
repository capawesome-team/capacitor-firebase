package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationConfig;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;

public class SignInWithCustomTokenOptions extends SignInOptions {

    @NonNull
    private final String token;

    public SignInWithCustomTokenOptions(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config) {
        super(call, config);
        this.token = SignInWithCustomTokenOptions.getTokenFromCall(call);
    }

    @NonNull
    public String getToken() {
        return token;
    }

    @NonNull
    private static String getTokenFromCall(@NonNull PluginCall call) {
        return call.getString("token", "");
    }
}
