package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.events;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;
import java.util.Set;

public class AddConfigUpdateListenerOptionsCallbackEvent implements Result {

    private Set<String> updatedKeys;

    public AddConfigUpdateListenerOptionsCallbackEvent(Set<String> updatedKeys) {
        this.updatedKeys = updatedKeys;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("updatedKeys", updatedKeys);
        return result;
    }
}
