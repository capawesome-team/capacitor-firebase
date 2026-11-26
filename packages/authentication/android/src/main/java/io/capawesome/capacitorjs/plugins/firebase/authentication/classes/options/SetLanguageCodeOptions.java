package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetLanguageCodeOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String languageCode;

    public SetLanguageCodeOptions(@NonNull PluginCall call) {
        this.languageCode = SetLanguageCodeOptions.getLanguageCodeFromCall(call);
        this.appName = SetLanguageCodeOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public String getLanguageCode() {
        return languageCode;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @NonNull
    private static String getLanguageCodeFromCall(@NonNull PluginCall call) {
        return call.getString("languageCode", "");
    }
}
