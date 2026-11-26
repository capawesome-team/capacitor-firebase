package io.capawesome.capacitorjs.plugins.firebase.functions.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.Result;
import org.json.JSONObject;

public class CallResult implements Result {

    @Nullable
    private Object data;

    public CallResult(@Nullable Object data) {
        this.data = data;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        // JSONObject.wrap converts nested maps and lists and keeps null values, which put would drop.
        result.put("data", JSONObject.wrap(data));
        return result;
    }
}
