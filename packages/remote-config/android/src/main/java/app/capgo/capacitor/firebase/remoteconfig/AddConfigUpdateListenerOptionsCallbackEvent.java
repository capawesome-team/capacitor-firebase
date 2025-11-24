package app.capgo.capacitor.firebase.remoteconfig.classes.events;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.firebase.remoteconfig.ConfigUpdate;
import app.capgo.capacitor.firebase.remoteconfig.interfaces.Result;
import java.util.Set;

public class AddConfigUpdateListenerOptionsCallbackEvent implements Result {

    private ConfigUpdate configUpdate;

    public AddConfigUpdateListenerOptionsCallbackEvent(ConfigUpdate configUpdate) {
        this.configUpdate = configUpdate;
    }

    public JSObject toJSObject() {
        JSArray updatedKeysResult = new JSArray();
        for (String key : configUpdate.getUpdatedKeys()) {
            updatedKeysResult.put(key);
        }

        JSObject result = new JSObject();
        result.put("updatedKeys", updatedKeysResult);
        return result;
    }
}
