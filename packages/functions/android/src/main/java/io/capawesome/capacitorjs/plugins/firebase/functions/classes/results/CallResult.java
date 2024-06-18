package io.capawesome.capacitorjs.plugins.firebase.functions.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;

import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.Result;

public class CallResult implements Result {
    @Nullable
    private Object data;

    public CallResult(@Nullable Object data) {
        this.data = data;
    }

    @NonNull
    public JSObject toJSObject() {
        Object dataResult = this.data;

        JSObject result = new JSObject();
        result.put("data", dataResult == null ? JSObject.NULL : dataResult);
        return result;
    }
}
