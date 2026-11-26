package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class ConfirmPasswordResetOptions {

    @NonNull
    private final String newPassword;

    @NonNull
    private final String oobCode;

    public ConfirmPasswordResetOptions(@NonNull PluginCall call) throws Exception {
        this.oobCode = ConfirmPasswordResetOptions.getOobCodeFromCall(call);
        this.newPassword = ConfirmPasswordResetOptions.getNewPasswordFromCall(call);
    }

    @NonNull
    public String getNewPassword() {
        return newPassword;
    }

    @NonNull
    public String getOobCode() {
        return oobCode;
    }

    @NonNull
    private static String getNewPasswordFromCall(@NonNull PluginCall call) throws Exception {
        String newPassword = call.getString("newPassword");
        if (newPassword == null) {
            throw CustomExceptions.NEW_PASSWORD_MISSING;
        }
        return newPassword;
    }

    @NonNull
    private static String getOobCodeFromCall(@NonNull PluginCall call) throws Exception {
        String oobCode = call.getString("oobCode");
        if (oobCode == null) {
            throw CustomExceptions.OOB_CODE_MISSING;
        }
        return oobCode;
    }
}
