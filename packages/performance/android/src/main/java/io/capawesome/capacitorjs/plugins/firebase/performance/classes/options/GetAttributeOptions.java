package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class GetAttributeOptions {

    @NonNull
    private final String attribute;

    @NonNull
    private final String traceName;

    public GetAttributeOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the traceName before the attribute, so a missing traceName is reported first.
        this.traceName = GetAttributeOptions.getTraceNameFromCall(call);
        this.attribute = GetAttributeOptions.getAttributeFromCall(call);
    }

    @NonNull
    public String getAttribute() {
        return attribute;
    }

    @NonNull
    public String getTraceName() {
        return traceName;
    }

    @NonNull
    private static String getAttributeFromCall(@NonNull PluginCall call) throws Exception {
        String attribute = call.getString("attribute");
        if (attribute == null) {
            throw CustomExceptions.ATTRIBUTE_MISSING;
        }
        return attribute;
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
