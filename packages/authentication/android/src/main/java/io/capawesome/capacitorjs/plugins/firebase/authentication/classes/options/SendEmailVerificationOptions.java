package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class SendEmailVerificationOptions {

    @Nullable
    private final JSObject actionCodeSettings;

    @Nullable
    private final String appName;

    public SendEmailVerificationOptions(@NonNull PluginCall call) {
        this.actionCodeSettings = SendEmailVerificationOptions.getActionCodeSettingsFromCall(call);
        this.appName = SendEmailVerificationOptions.getAppNameFromCall(call);
    }

    @Nullable
    public JSObject getActionCodeSettings() {
        return actionCodeSettings;
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @Nullable
    private static JSObject getActionCodeSettingsFromCall(@NonNull PluginCall call) {
        return call.getObject("actionCodeSettings");
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }
}
