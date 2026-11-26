package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class UpdatePasswordOptions {

    @NonNull
    private final String newPassword;

    public UpdatePasswordOptions(@NonNull PluginCall call) throws Exception {
        this.newPassword = UpdatePasswordOptions.getNewPasswordFromCall(call);
    }

    @NonNull
    public String getNewPassword() {
        return newPassword;
    }

    @NonNull
    private static String getNewPasswordFromCall(@NonNull PluginCall call) throws Exception {
        String newPassword = call.getString("newPassword");
        if (newPassword == null) {
            throw CustomExceptions.NEW_PASSWORD_MISSING;
        }
        return newPassword;
    }
}
