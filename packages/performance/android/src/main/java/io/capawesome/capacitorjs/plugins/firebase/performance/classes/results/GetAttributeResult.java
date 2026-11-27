package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;

public class GetAttributeResult implements Result {

    @Nullable
    private final String value;

    public GetAttributeResult(@Nullable String value) {
        this.value = value;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        // NEVER put JSONObject.NULL here before the next major version. Until then a missing value leaves the key out.
        if (value != null) {
            result.put("value", value);
        }
        return result;
    }
}
