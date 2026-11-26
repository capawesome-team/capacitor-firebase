package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class GetIdTokenOptions {

    @Nullable
    private final String appName;

    private final boolean forceRefresh;

    public GetIdTokenOptions(@NonNull PluginCall call) {
        this.forceRefresh = GetIdTokenOptions.getForceRefreshFromCall(call);
        this.appName = GetIdTokenOptions.getAppNameFromCall(call);
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
