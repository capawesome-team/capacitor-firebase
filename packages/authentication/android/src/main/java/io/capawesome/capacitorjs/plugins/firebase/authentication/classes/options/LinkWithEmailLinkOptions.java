package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class LinkWithEmailLinkOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    @NonNull
    private final String emailLink;

    public LinkWithEmailLinkOptions(@NonNull PluginCall call) {
        this.email = LinkWithEmailLinkOptions.getEmailFromCall(call);
        this.emailLink = LinkWithEmailLinkOptions.getEmailLinkFromCall(call);
        this.appName = LinkWithEmailLinkOptions.getAppNameFromCall(call);
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
    public String getEmailLink() {
        return emailLink;
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
    private static String getEmailLinkFromCall(@NonNull PluginCall call) {
        return call.getString("emailLink", "");
    }
}
