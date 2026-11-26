package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class ApplyActionCodeOptions {

    @NonNull
    private final String oobCode;

    public ApplyActionCodeOptions(@NonNull PluginCall call) throws Exception {
        this.oobCode = ApplyActionCodeOptions.getOobCodeFromCall(call);
    }

    @NonNull
    public String getOobCode() {
        return oobCode;
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
