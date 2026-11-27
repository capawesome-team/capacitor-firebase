package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;

public class GetBooleanResult implements Result {

    private final int source;

    private final boolean value;

    public GetBooleanResult(boolean value, int source) {
        this.value = value;
        this.source = source;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", value);
        result.put("source", source);
        return result;
    }
}
