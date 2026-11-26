package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class SendEmailVerificationOptions {

    @Nullable
    private final JSObject actionCodeSettings;

    public SendEmailVerificationOptions(@NonNull PluginCall call) {
        this.actionCodeSettings = SendEmailVerificationOptions.getActionCodeSettingsFromCall(call);
    }

    @Nullable
    public JSObject getActionCodeSettings() {
        return actionCodeSettings;
    }

    @Nullable
    private static JSObject getActionCodeSettingsFromCall(@NonNull PluginCall call) {
        return call.getObject("actionCodeSettings");
    }
}
