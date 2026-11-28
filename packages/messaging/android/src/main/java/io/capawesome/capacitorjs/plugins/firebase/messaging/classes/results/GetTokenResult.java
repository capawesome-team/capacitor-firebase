package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class GetTokenResult implements Result {

    @Nullable
    private final String token;

    public GetTokenResult(@Nullable String token) {
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
