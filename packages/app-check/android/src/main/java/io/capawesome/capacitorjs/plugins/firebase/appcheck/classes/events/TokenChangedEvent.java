package io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.Result;

public class TokenChangedEvent implements Result {

    @NonNull
    private final String token;

    public TokenChangedEvent(@NonNull String token) {
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
