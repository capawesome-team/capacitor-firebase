package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class LinkWithEmailLinkOptions {

    @NonNull
    private final String email;

    @NonNull
    private final String emailLink;

    public LinkWithEmailLinkOptions(@NonNull PluginCall call) {
        this.email = LinkWithEmailLinkOptions.getEmailFromCall(call);
        this.emailLink = LinkWithEmailLinkOptions.getEmailLinkFromCall(call);
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
