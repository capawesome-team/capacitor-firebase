package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class LinkWithEmailAndPasswordOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    @NonNull
    private final String password;

    public LinkWithEmailAndPasswordOptions(@NonNull PluginCall call) {
        this.email = LinkWithEmailAndPasswordOptions.getEmailFromCall(call);
        this.password = LinkWithEmailAndPasswordOptions.getPasswordFromCall(call);
        this.appName = LinkWithEmailAndPasswordOptions.getAppNameFromCall(call);
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
