package io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.CustomExceptions;

public class SetTokenAutoRefreshEnabledOptions {

    private final boolean enabled;

    public SetTokenAutoRefreshEnabledOptions(@NonNull PluginCall call) throws Exception {
        this.enabled = SetTokenAutoRefreshEnabledOptions.getEnabledFromCall(call);
    }

    public boolean getEnabled() {
        return enabled;
    }

    private static boolean getEnabledFromCall(@NonNull PluginCall call) throws Exception {
        Boolean enabled = call.getBoolean("enabled");
        if (enabled == null) {
            throw CustomExceptions.ENABLED_MISSING;
        }
        return enabled;
    }
}
