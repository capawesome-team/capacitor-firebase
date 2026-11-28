package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class IsSupportedResult implements Result {

    private final boolean isSupported;

    public IsSupportedResult(boolean isSupported) {
        this.isSupported = isSupported;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("isSupported", isSupported);
        return result;
    }
}
