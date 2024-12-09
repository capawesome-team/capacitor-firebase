package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

public class GetAttributeResult {

    private final String value;

    public GetAttributeResult(@Nullable String value) {
        this.value = value;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", this.value);
        return result;
    }
}
