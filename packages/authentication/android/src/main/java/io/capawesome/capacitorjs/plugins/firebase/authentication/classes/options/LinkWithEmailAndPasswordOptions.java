package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class LinkWithEmailAndPasswordOptions {

    @NonNull
    private final String email;

    @NonNull
    private final String password;

    public LinkWithEmailAndPasswordOptions(@NonNull PluginCall call) {
        this.email = LinkWithEmailAndPasswordOptions.getEmailFromCall(call);
        this.password = LinkWithEmailAndPasswordOptions.getPasswordFromCall(call);
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
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
