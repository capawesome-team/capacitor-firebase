package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class PermissionStatusResult implements Result {

    @NonNull
    private final String receive;

    public PermissionStatusResult(@NonNull String receive) {
        this.receive = receive;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("receive", receive);
        return result;
    }
}
