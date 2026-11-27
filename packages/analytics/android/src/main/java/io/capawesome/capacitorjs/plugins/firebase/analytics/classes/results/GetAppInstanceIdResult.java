package io.capawesome.capacitorjs.plugins.firebase.analytics.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.analytics.interfaces.Result;

public class GetAppInstanceIdResult implements Result {

    @Nullable
    private final String appInstanceId;

    public GetAppInstanceIdResult(@Nullable String appInstanceId) {
        this.appInstanceId = appInstanceId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (appInstanceId != null) {
            result.put("appInstanceId", appInstanceId);
        }
        return result;
    }
}
