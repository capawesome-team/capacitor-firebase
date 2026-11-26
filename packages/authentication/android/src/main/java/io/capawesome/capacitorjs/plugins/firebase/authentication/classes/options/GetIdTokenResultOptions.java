package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class GetIdTokenResultOptions {

    private final boolean forceRefresh;

    public GetIdTokenResultOptions(@NonNull PluginCall call) {
        this.forceRefresh = GetIdTokenResultOptions.getForceRefreshFromCall(call);
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    private static boolean getForceRefreshFromCall(@NonNull PluginCall call) {
        return call.getBoolean("forceRefresh", false);
    }
}
