package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class SetTenantIdOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String tenantId;

    public SetTenantIdOptions(@NonNull PluginCall call) throws Exception {
        this.tenantId = SetTenantIdOptions.getTenantIdFromCall(call);
        this.appName = SetTenantIdOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getTenantId() {
        return tenantId;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
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
