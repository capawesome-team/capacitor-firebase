package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class SendSignInLinkToEmailOptions {

    @NonNull
    private final JSObject actionCodeSettings;

    @Nullable
    private final String appName;

    @NonNull
    private final String email;

    public SendSignInLinkToEmailOptions(@NonNull PluginCall call) throws Exception {
        this.email = SendSignInLinkToEmailOptions.getEmailFromCall(call);
        this.actionCodeSettings = SendSignInLinkToEmailOptions.getActionCodeSettingsFromCall(call);
        this.appName = SendSignInLinkToEmailOptions.getAppNameFromCall(call);
    }

    @NonNull
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

    @NonNull
    private static JSObject getActionCodeSettingsFromCall(@NonNull PluginCall call) throws Exception {
        JSObject actionCodeSettings = call.getObject("actionCodeSettings");
        if (actionCodeSettings == null) {
            throw CustomExceptions.ACTION_CODE_SETTINGS_MISSING;
        }
        return actionCodeSettings;
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
