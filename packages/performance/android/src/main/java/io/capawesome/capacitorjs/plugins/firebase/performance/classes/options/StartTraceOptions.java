package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class StartTraceOptions {

    @NonNull
    private final String traceName;

    public StartTraceOptions(@NonNull PluginCall call) throws Exception {
        this.traceName = StartTraceOptions.getTraceNameFromCall(call);
    }

    @NonNull
    public String getTraceName() {
        return traceName;
    }

    @NonNull
    private static String getTraceNameFromCall(@NonNull PluginCall call) throws Exception {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw CustomExceptions.TRACE_NAME_MISSING;
        }
        if (!StartTraceOptions.isValidTraceName(traceName)) {
            throw CustomExceptions.TRACE_NAME_INVALID;
        }
        return traceName;
    }

    /**
     * Refuses the names that the Firebase SDK refuses or never records, and the names of its own traces, which iOS refuses too.
     * It trims the name as the SDK does before its own check.
     */
    private static boolean isValidTraceName(@NonNull String traceName) {
        String trimmedTraceName = traceName.trim();
        return !trimmedTraceName.isEmpty() && trimmedTraceName.length() <= 100 && !trimmedTraceName.startsWith("_");
    }
}
