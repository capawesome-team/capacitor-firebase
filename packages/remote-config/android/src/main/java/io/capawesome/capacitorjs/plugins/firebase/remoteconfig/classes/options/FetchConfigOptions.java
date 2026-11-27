package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class FetchConfigOptions {

    private final long minimumFetchIntervalInSeconds;

    public FetchConfigOptions(@NonNull PluginCall call) {
        this.minimumFetchIntervalInSeconds = FetchConfigOptions.getMinimumFetchIntervalInSecondsFromCall(call);
    }

    public long getMinimumFetchIntervalInSeconds() {
        return minimumFetchIntervalInSeconds;
    }

    private static long getMinimumFetchIntervalInSecondsFromCall(@NonNull PluginCall call) {
        return call.getInt("minimumFetchIntervalInSeconds", 43200);
    }
}
