package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class GetMetricResult {

    private final long num;

    public GetMetricResult(@NonNull long value) {
        this.num = value;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", this.num == 0 ? null : this.num);
        return result;
    }
}
