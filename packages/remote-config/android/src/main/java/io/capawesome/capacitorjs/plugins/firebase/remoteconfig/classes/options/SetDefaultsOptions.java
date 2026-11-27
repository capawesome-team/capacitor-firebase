package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SetDefaultsOptions {

    @NonNull
    private final Map<String, Object> defaults;

    public SetDefaultsOptions(@NonNull PluginCall call) throws Exception {
        this.defaults = SetDefaultsOptions.getDefaultsFromCall(call);
    }

    @NonNull
    public Map<String, Object> getDefaults() {
        return defaults;
    }

    @NonNull
    private static Map<String, Object> getDefaultsFromCall(@NonNull PluginCall call) throws Exception {
        JSObject defaultsObject = call.getObject("defaults");
        if (defaultsObject == null) {
            throw CustomExceptions.DEFAULTS_MISSING;
        }
        Map<String, Object> defaults = new HashMap<>();
        Iterator<String> keys = defaultsObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            defaults.put(key, defaultsObject.get(key));
        }
        return defaults;
    }
}
