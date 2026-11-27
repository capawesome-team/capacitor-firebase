package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class SetSettingsOptions {

    private final int fetchTimeoutInSeconds;

    private final int minimumFetchIntervalInSeconds;

    public SetSettingsOptions(@NonNull PluginCall call) {
        this.fetchTimeoutInSeconds = SetSettingsOptions.getFetchTimeoutInSecondsFromCall(call);
        this.minimumFetchIntervalInSeconds = SetSettingsOptions.getMinimumFetchIntervalInSecondsFromCall(call);
    }

    public int getFetchTimeoutInSeconds() {
        return fetchTimeoutInSeconds;
    }

    public int getMinimumFetchIntervalInSeconds() {
        return minimumFetchIntervalInSeconds;
    }

    private static int getFetchTimeoutInSecondsFromCall(@NonNull PluginCall call) {
        return call.getInt("fetchTimeoutInSeconds", 60);
    }

    private static int getMinimumFetchIntervalInSecondsFromCall(@NonNull PluginCall call) {
        return call.getInt("minimumFetchIntervalInSeconds", 43200);
    }
}
