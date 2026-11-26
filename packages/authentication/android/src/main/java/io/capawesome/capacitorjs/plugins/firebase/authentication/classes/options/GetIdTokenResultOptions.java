package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class GetIdTokenResultOptions {

    @Nullable
    private final String appName;

    private final boolean forceRefresh;

    public GetIdTokenResultOptions(@NonNull PluginCall call) {
        this.forceRefresh = GetIdTokenResultOptions.getForceRefreshFromCall(call);
        this.appName = GetIdTokenResultOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    private static boolean getForceRefreshFromCall(@NonNull PluginCall call) {
        return call.getBoolean("forceRefresh", false);
    }
}
