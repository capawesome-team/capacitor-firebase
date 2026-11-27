package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class PutAttributeOptions {

    @NonNull
    private final String attribute;

    @NonNull
    private final String traceName;

    @NonNull
    private final String value;

    public PutAttributeOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the traceName, the attribute and the value in this order, so the first missing one is reported.
        this.traceName = PutAttributeOptions.getTraceNameFromCall(call);
        this.attribute = PutAttributeOptions.getAttributeFromCall(call);
        this.value = PutAttributeOptions.getValueFromCall(call);
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
    public String getValue() {
        return value;
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

    @NonNull
    private static String getValueFromCall(@NonNull PluginCall call) throws Exception {
        String value = call.getString("value");
        if (value == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        return value;
    }
}
