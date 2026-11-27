package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;

public class GetMetricResult implements Result {

    private final long value;

    public GetMetricResult(long value) {
        this.value = value;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", value);
        return result;
    }
}
