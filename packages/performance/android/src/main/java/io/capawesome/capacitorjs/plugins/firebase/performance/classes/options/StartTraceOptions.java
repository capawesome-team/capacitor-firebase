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
        return traceName;
    }
}
