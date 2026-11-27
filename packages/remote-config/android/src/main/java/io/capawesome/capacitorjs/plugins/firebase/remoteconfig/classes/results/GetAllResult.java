package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;
import java.util.Map;

public class GetAllResult implements Result {

    @NonNull
    private final Map<String, FirebaseRemoteConfigValue> values;

    public GetAllResult(@NonNull Map<String, FirebaseRemoteConfigValue> values) {
        this.values = values;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject valuesObject = new JSObject();
        for (Map.Entry<String, FirebaseRemoteConfigValue> entry : values.entrySet()) {
            FirebaseRemoteConfigValue value = entry.getValue();
            JSObject valueObject = new JSObject();
            valueObject.put("value", value.asString());
            valueObject.put("source", value.getSource());
            valuesObject.put(entry.getKey(), valueObject);
        }
        JSObject result = new JSObject();
        result.put("values", valuesObject);
        return result;
    }
}
