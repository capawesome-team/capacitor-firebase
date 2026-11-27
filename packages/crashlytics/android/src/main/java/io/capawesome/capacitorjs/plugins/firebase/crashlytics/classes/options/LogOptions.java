package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomExceptions;

public class LogOptions {

    @NonNull
    private final String message;

    public LogOptions(@NonNull PluginCall call) throws Exception {
        this.message = LogOptions.getMessageFromCall(call);
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    private static String getMessageFromCall(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw CustomExceptions.MESSAGE_MISSING;
        }
        return message;
    }
}
