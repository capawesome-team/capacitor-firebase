package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class VerifyBeforeUpdateEmailOptions {

    @Nullable
    private final JSObject actionCodeSettings;

    @NonNull
    private final String newEmail;

    public VerifyBeforeUpdateEmailOptions(@NonNull PluginCall call) throws Exception {
        this.newEmail = VerifyBeforeUpdateEmailOptions.getNewEmailFromCall(call);
        this.actionCodeSettings = VerifyBeforeUpdateEmailOptions.getActionCodeSettingsFromCall(call);
    }

    @Nullable
    public JSObject getActionCodeSettings() {
        return actionCodeSettings;
    }

    @NonNull
    public String getNewEmail() {
        return newEmail;
    }

    @Nullable
    private static JSObject getActionCodeSettingsFromCall(@NonNull PluginCall call) {
        return call.getObject("actionCodeSettings");
    }

    @NonNull
    private static String getNewEmailFromCall(@NonNull PluginCall call) throws Exception {
        String newEmail = call.getString("newEmail");
        if (newEmail == null) {
            throw CustomExceptions.NEW_EMAIL_MISSING;
        }
        return newEmail;
    }
}
