package io.capawesome.capacitorjs.plugins.firebase.app.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.firebase.FirebaseOptions;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.Result;
import java.util.Objects;

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
        result.put("apiKey", options.getApiKey());
        result.put("applicationId", options.getApplicationId());
        result.put("databaseUrl", Objects.toString(options.getDatabaseUrl(), ""));
        result.put("gcmSenderId", Objects.toString(options.getGcmSenderId(), ""));
        result.put("projectId", Objects.toString(options.getProjectId(), ""));
        result.put("storageBucket", Objects.toString(options.getStorageBucket(), ""));
        return result;
    }
}
