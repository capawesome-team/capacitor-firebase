package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;

public class GetStringResult implements Result {

    private final int source;

    @NonNull
    private final String value;

    public GetStringResult(@NonNull String value, int source) {
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
