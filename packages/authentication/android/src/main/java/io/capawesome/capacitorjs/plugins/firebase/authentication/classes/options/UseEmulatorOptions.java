package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class UseEmulatorOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String host;

    private final int port;

    public UseEmulatorOptions(@NonNull PluginCall call) throws Exception {
        this.host = UseEmulatorOptions.getHostFromCall(call);
        this.port = UseEmulatorOptions.getPortFromCall(call);
        this.appName = UseEmulatorOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
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
        return call.getInt("port", 9099);
    }
}
