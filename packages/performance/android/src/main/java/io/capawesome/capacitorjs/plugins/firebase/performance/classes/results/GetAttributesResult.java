package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;
import java.util.Map;

public class GetAttributesResult implements Result {

    @NonNull
    private final Map<String, String> attributes;

    public GetAttributesResult(@NonNull Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject attributesObject = new JSObject();
        for (String attribute : attributes.keySet()) {
            attributesObject.put(attribute, attributes.get(attribute));
        }
        JSObject result = new JSObject();
        result.put("attributes", attributesObject);
        // The old key `result` stays until the next major version, so apps that read it keep working.
        result.put("result", attributesObject);
        return result;
    }
}
