package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class UpdateEmailOptions {

    @NonNull
    private final String newEmail;

    public UpdateEmailOptions(@NonNull PluginCall call) throws Exception {
        this.newEmail = UpdateEmailOptions.getNewEmailFromCall(call);
    }

    @NonNull
    public String getNewEmail() {
        return newEmail;
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
