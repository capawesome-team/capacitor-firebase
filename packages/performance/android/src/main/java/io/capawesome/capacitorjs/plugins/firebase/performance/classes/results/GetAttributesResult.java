package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import java.util.Map;

public class GetAttributesResult {

    private final Map<String, String> attributesMap;

    public GetAttributesResult(@NonNull Map<String, String> value) {
        this.attributesMap = value;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        JSObject resultMap = new JSObject();
        for (String attribute : this.attributesMap.keySet()) {
            resultMap.put(attribute, this.attributesMap.get(attribute));
        }
        result.put("result", resultMap);
        return result;
    }
}
