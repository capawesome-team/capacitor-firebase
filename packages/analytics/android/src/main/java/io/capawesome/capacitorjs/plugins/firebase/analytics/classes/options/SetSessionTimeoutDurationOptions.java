package io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class SetSessionTimeoutDurationOptions {

    private final int duration;

    public SetSessionTimeoutDurationOptions(@NonNull PluginCall call) {
        this.duration = SetSessionTimeoutDurationOptions.getDurationFromCall(call);
    }

    public int getDuration() {
        return duration;
    }

    private static int getDurationFromCall(@NonNull PluginCall call) {
        return call.getInt("duration", 1800);
    }
}
