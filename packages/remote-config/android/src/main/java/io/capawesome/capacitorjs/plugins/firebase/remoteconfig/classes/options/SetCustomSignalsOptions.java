package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SetCustomSignalsOptions {

    @NonNull
    private final Map<String, Object> customSignals;

    public SetCustomSignalsOptions(@NonNull PluginCall call) throws Exception {
        this.customSignals = SetCustomSignalsOptions.getCustomSignalsFromCall(call);
    }

    @NonNull
    public Map<String, Object> getCustomSignals() {
        return customSignals;
    }

    @NonNull
    private static Map<String, Object> getCustomSignalsFromCall(@NonNull PluginCall call) throws Exception {
        JSObject customSignalsObject = call.getObject("customSignals");
        if (customSignalsObject == null) {
            throw CustomExceptions.CUSTOM_SIGNALS_MISSING;
        }
        Map<String, Object> customSignals = new HashMap<>();
        Iterator<String> keys = customSignalsObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            customSignals.put(key, customSignalsObject.get(key));
        }
        return customSignals;
    }
}
