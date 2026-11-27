package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.CustomExceptions;

public class UseEmulatorOptions {

    @NonNull
    private final String host;

    private final int port;

    @Nullable
    private final String regionOrCustomDomain;

    public UseEmulatorOptions(@NonNull PluginCall call) throws Exception {
        this.host = UseEmulatorOptions.getHostFromCall(call);
        this.port = UseEmulatorOptions.getPortFromCall(call);
        this.regionOrCustomDomain = UseEmulatorOptions.getRegionOrCustomDomainFromCall(call);
    }

    @NonNull
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Nullable
    public String getRegionOrCustomDomain() {
        return regionOrCustomDomain;
    }

    @NonNull
    private static String getHostFromCall(@NonNull PluginCall call) throws Exception {
        String host = call.getString("host");
        if (host == null) {
            throw CustomExceptions.HOST_MISSING;
        }
        return host;
    }

    private static int getPortFromCall(@NonNull PluginCall call) {
        return call.getInt("port", 5001);
    }

    @Nullable
    private static String getRegionOrCustomDomainFromCall(@NonNull PluginCall call) {
        return call.getString("regionOrCustomDomain", null);
    }
}
