package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class ConfirmPasswordResetOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String newPassword;

    @NonNull
    private final String oobCode;

    public ConfirmPasswordResetOptions(@NonNull PluginCall call) throws Exception {
        this.oobCode = ConfirmPasswordResetOptions.getOobCodeFromCall(call);
        this.newPassword = ConfirmPasswordResetOptions.getNewPasswordFromCall(call);
        this.appName = ConfirmPasswordResetOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getNewPassword() {
        return newPassword;
    }

    @NonNull
    public String getOobCode() {
        return oobCode;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
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
