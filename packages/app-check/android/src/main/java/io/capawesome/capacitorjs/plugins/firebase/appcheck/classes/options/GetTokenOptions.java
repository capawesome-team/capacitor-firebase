package io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class GetTokenOptions {

    private final boolean forceRefresh;

    public GetTokenOptions(@NonNull PluginCall call) {
        this.forceRefresh = GetTokenOptions.getForceRefreshFromCall(call);
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    private static boolean getForceRefreshFromCall(@NonNull PluginCall call) {
        return call.getBoolean("forceRefresh", false);
    }
}
