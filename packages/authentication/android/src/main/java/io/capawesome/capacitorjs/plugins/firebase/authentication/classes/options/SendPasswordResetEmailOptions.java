package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class SendPasswordResetEmailOptions {

    @Nullable
    private final JSObject actionCodeSettings;

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    public SendPasswordResetEmailOptions(@NonNull PluginCall call) throws Exception {
        this.email = SendPasswordResetEmailOptions.getEmailFromCall(call);
        this.actionCodeSettings = SendPasswordResetEmailOptions.getActionCodeSettingsFromCall(call);
        this.appName = SendPasswordResetEmailOptions.getAppNameFromCall(call);
    }

    @Nullable
    public JSObject getActionCodeSettings() {
        return actionCodeSettings;
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    private static JSObject getActionCodeSettingsFromCall(@NonNull PluginCall call) {
        return call.getObject("actionCodeSettings");
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
