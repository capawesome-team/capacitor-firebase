package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationConfig;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class CreateUserWithEmailAndPasswordOptions {

    @NonNull
    private final String email;

    @NonNull
    private final String password;

    public CreateUserWithEmailAndPasswordOptions(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config) throws Exception {
        // ALWAYS check skipNativeAuth first. Its error must win over a missing email or password.
        CreateUserWithEmailAndPasswordOptions.checkSkipNativeAuthFromCall(call, config);
        this.email = CreateUserWithEmailAndPasswordOptions.getEmailFromCall(call);
        this.password = CreateUserWithEmailAndPasswordOptions.getPasswordFromCall(call);
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    private static void checkSkipNativeAuthFromCall(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config)
        throws Exception {
        boolean skipNativeAuth = call.getBoolean("skipNativeAuth", config.getSkipNativeAuth());
        if (skipNativeAuth) {
            throw CustomExceptions.EMAIL_SIGN_IN_SKIP_NATIVE_AUTH;
        }
    }

    @NonNull
    private static String getEmailFromCall(@NonNull PluginCall call) throws Exception {
        String email = call.getString("email");
        if (email == null) {
            throw CustomExceptions.EMAIL_MISSING;
        }
        return email;
    }

    @NonNull
    private static String getPasswordFromCall(@NonNull PluginCall call) throws Exception {
        String password = call.getString("password");
        if (password == null) {
            throw CustomExceptions.PASSWORD_MISSING;
        }
        return password;
    }
}
