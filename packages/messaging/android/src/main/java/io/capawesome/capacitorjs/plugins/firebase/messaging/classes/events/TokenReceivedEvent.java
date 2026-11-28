package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class TokenReceivedEvent implements Result {

    @NonNull
    private final String token;

    public TokenReceivedEvent(@NonNull String token) {
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
