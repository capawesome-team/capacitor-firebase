package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;
import org.json.JSONObject;

public class GetTenantIdResult implements Result {

    @Nullable
    private final String tenantId;

    public GetTenantIdResult(@Nullable String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("tenantId", (tenantId == null ? JSONObject.NULL : tenantId));
        return result;
    }
}
