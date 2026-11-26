package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class GetIdTokenOptions {

    private final boolean forceRefresh;

    public GetIdTokenOptions(@NonNull PluginCall call) {
        this.forceRefresh = GetIdTokenOptions.getForceRefreshFromCall(call);
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    private static boolean getForceRefreshFromCall(@NonNull PluginCall call) {
        return call.getBoolean("forceRefresh", false);
    }
}
