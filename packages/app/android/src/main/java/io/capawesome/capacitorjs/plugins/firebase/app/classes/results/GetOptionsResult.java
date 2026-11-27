package io.capawesome.capacitorjs.plugins.firebase.app.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.firebase.FirebaseOptions;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.Result;

public class GetOptionsResult implements Result {

    @NonNull
    private final FirebaseOptions options;

    public GetOptionsResult(@NonNull FirebaseOptions options) {
        this.options = options;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        // NEVER put JSONObject.NULL or "" here before the next major version. Until then a missing value leaves the key out.
        result.put("apiKey", options.getApiKey());
        result.put("applicationId", options.getApplicationId());
        result.put("databaseUrl", options.getDatabaseUrl());
        result.put("gcmSenderId", options.getGcmSenderId());
        result.put("projectId", options.getProjectId());
        result.put("storageBucket", options.getStorageBucket());
        return result;
    }
}
