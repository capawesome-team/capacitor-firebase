package io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetUserIdOptions {

    @Nullable
    private final String userId;

    public SetUserIdOptions(@NonNull PluginCall call) {
        this.userId = SetUserIdOptions.getUserIdFromCall(call);
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @Nullable
    private static String getUserIdFromCall(@NonNull PluginCall call) {
        return call.getString("userId", null);
    }
}
