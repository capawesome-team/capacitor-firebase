package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationConfig;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInOptions;

public class SignInWithEmailLinkOptions extends SignInOptions {

    @NonNull
    private final String email;

    @NonNull
    private final String emailLink;

    public SignInWithEmailLinkOptions(@NonNull PluginCall call, @NonNull FirebaseAuthenticationConfig config) {
        super(call, config);
        this.email = SignInWithEmailLinkOptions.getEmailFromCall(call);
        this.emailLink = SignInWithEmailLinkOptions.getEmailLinkFromCall(call);
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getEmailLink() {
        return emailLink;
    }

    @NonNull
    private static String getEmailFromCall(@NonNull PluginCall call) {
        return call.getString("email", "");
    }

    @NonNull
    private static String getEmailLinkFromCall(@NonNull PluginCall call) {
        return call.getString("emailLink", "");
    }
}
