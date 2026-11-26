package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class UnlinkOptions {

    @NonNull
    private final String providerId;

    public UnlinkOptions(@NonNull PluginCall call) throws Exception {
        this.providerId = UnlinkOptions.getProviderIdFromCall(call);
    }

    @NonNull
    public String getProviderId() {
        return providerId;
    }

    @NonNull
    private static String getProviderIdFromCall(@NonNull PluginCall call) throws Exception {
        String providerId = call.getString("providerId");
        if (providerId == null) {
            throw CustomExceptions.PROVIDER_ID_MISSING;
        }
        return providerId;
    }
}
