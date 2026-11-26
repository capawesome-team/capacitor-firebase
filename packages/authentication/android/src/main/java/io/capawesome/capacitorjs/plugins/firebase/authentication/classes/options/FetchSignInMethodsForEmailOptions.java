package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class FetchSignInMethodsForEmailOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    public FetchSignInMethodsForEmailOptions(@NonNull PluginCall call) throws Exception {
        this.email = FetchSignInMethodsForEmailOptions.getEmailFromCall(call);
        this.appName = FetchSignInMethodsForEmailOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getEmail() {
        return this.email;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @NonNull
    private static String getEmailFromCall(@NonNull PluginCall call) throws Exception {
        String email = call.getString("email");
        if (email == null) {
            throw CustomExceptions.EMAIL_MISSING;
        }
        return email;
    }
}
