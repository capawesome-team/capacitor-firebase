package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationConfig;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;

public class SignInWithEmailAndPasswordOptions extends SignInOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    @NonNull
    private final String password;

    public SignInWithEmailAndPasswordOptions(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config) {
        super(call, config);
        this.email = SignInWithEmailAndPasswordOptions.getEmailFromCall(call);
        this.password = SignInWithEmailAndPasswordOptions.getPasswordFromCall(call);
        this.appName = SignInWithEmailAndPasswordOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @NonNull
    private static String getEmailFromCall(@NonNull PluginCall call) {
        return call.getString("email", "");
    }

    @NonNull
    private static String getPasswordFromCall(@NonNull PluginCall call) {
        return call.getString("password", "");
    }
}
