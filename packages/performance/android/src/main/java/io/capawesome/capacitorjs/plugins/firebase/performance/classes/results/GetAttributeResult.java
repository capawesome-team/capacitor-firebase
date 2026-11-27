package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;
import org.json.JSONObject;

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
        result.put("value", value == null ? JSONObject.NULL : value);
        return result;
    }
}
