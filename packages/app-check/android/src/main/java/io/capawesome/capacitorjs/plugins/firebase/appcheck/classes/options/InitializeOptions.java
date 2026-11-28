package io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class InitializeOptions {

    private final boolean debug;

    private final boolean isTokenAutoRefreshEnabled;

    public InitializeOptions(@NonNull PluginCall call) {
        this.debug = InitializeOptions.getDebugFromCall(call);
        this.isTokenAutoRefreshEnabled = InitializeOptions.getIsTokenAutoRefreshEnabledFromCall(call);
    }

    public boolean getDebug() {
        return debug;
    }

    public boolean isTokenAutoRefreshEnabled() {
        return isTokenAutoRefreshEnabled;
    }

    private static boolean getDebugFromCall(@NonNull PluginCall call) {
        Boolean debugTokenBoolean = call.getBoolean("debugToken", false);
        if (Boolean.TRUE.equals(debugTokenBoolean)) {
            return true;
        }
        String debugTokenString = call.getString("debugToken", "");
        if (debugTokenString != null && !debugTokenString.isEmpty()) {
            return true;
        }
        Boolean debug = call.getBoolean("debug", false);
        return Boolean.TRUE.equals(debug);
    }

    private static boolean getIsTokenAutoRefreshEnabledFromCall(@NonNull PluginCall call) {
        return call.getBoolean("isTokenAutoRefreshEnabled", false);
    }
}
