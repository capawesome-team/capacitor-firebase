package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class SetLanguageCodeOptions {

    @NonNull
    private final String languageCode;

    public SetLanguageCodeOptions(@NonNull PluginCall call) {
        this.languageCode = SetLanguageCodeOptions.getLanguageCodeFromCall(call);
    }

    @NonNull
    public String getLanguageCode() {
        return languageCode;
    }

    @NonNull
    private static String getLanguageCodeFromCall(@NonNull PluginCall call) {
        return call.getString("languageCode", "");
    }
}
