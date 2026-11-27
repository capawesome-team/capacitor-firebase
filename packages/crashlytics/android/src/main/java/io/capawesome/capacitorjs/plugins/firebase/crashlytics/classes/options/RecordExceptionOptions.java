package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomExceptions;

public class RecordExceptionOptions {

    @Nullable
    private final JSArray keysAndValues;

    @NonNull
    private final String message;

    @Nullable
    private final JSArray stacktrace;

    public RecordExceptionOptions(@NonNull PluginCall call) throws Exception {
        this.message = RecordExceptionOptions.getMessageFromCall(call);
        this.stacktrace = RecordExceptionOptions.getStacktraceFromCall(call);
        this.keysAndValues = RecordExceptionOptions.getKeysAndValuesFromCall(call);
    }

    @Nullable
    public JSArray getKeysAndValues() {
        return keysAndValues;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Nullable
    public JSArray getStacktrace() {
        return stacktrace;
    }

    @Nullable
    private static JSArray getKeysAndValuesFromCall(@NonNull PluginCall call) {
        return call.getArray("keysAndValues", null);
    }

    @NonNull
    private static String getMessageFromCall(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw CustomExceptions.MESSAGE_MISSING;
        }
        return message;
    }

    @Nullable
    private static JSArray getStacktraceFromCall(@NonNull PluginCall call) {
        return call.getArray("stacktrace", null);
    }
}
