package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;

public class IdTokenChangeEvent implements Result {

    @Nullable
    private final String token;

    public IdTokenChangeEvent(@Nullable String token) {
        this.token = token;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("token", token);
        return result;
    }
}
