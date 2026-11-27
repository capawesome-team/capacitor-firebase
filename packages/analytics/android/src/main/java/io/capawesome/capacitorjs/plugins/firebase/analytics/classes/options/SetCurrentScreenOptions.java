package io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetCurrentScreenOptions {

    @Nullable
    private final String screenClassOverride;

    @Nullable
    private final String screenName;

    public SetCurrentScreenOptions(@NonNull PluginCall call) {
        this.screenName = SetCurrentScreenOptions.getScreenNameFromCall(call);
        this.screenClassOverride = SetCurrentScreenOptions.getScreenClassOverrideFromCall(call);
    }

    @Nullable
    public String getScreenClassOverride() {
        return screenClassOverride;
    }

    @Nullable
    public String getScreenName() {
        return screenName;
    }

    @Nullable
    private static String getScreenClassOverrideFromCall(@NonNull PluginCall call) {
        return call.getString("screenClassOverride", null);
    }

    @Nullable
    private static String getScreenNameFromCall(@NonNull PluginCall call) {
        return call.getString("screenName", null);
    }
}
