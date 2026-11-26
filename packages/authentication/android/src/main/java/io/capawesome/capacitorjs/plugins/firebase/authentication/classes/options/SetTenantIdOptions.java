package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class SetTenantIdOptions {

    @NonNull
    private final String tenantId;

    public SetTenantIdOptions(@NonNull PluginCall call) throws Exception {
        this.tenantId = SetTenantIdOptions.getTenantIdFromCall(call);
    }

    @NonNull
    public String getTenantId() {
        return tenantId;
    }

    @NonNull
    private static String getTenantIdFromCall(@NonNull PluginCall call) throws Exception {
        String tenantId = call.getString("tenantId");
        if (tenantId == null) {
            throw CustomExceptions.TENANT_ID_MISSING;
        }
        return tenantId;
    }
}
