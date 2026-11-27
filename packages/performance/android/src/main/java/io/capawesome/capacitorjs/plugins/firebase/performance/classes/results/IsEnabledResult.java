package io.capawesome.capacitorjs.plugins.firebase.performance.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;

public class IsEnabledResult implements Result {

    private final boolean enabled;

    public IsEnabledResult(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("enabled", enabled);
        return result;
    }
}
