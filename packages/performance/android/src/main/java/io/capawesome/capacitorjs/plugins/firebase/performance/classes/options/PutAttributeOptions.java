package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.FirebasePerformancePlugin;

public class PutAttributeOptions {

    private final String traceName;
    private final String attribute;
    private final String value;

    public static class PutAttributeOptionsException extends Exception {

        public PutAttributeOptionsException(String message) {
            super(message);
        }
    }

    public PutAttributeOptions(PluginCall call) throws PutAttributeOptionsException {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw new PutAttributeOptionsException(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
        }
        this.traceName = traceName;
        String attribute = call.getString("attribute");
        if (attribute == null) {
            throw new PutAttributeOptionsException(FirebasePerformancePlugin.ERROR_ATTRIBUTE_MISSING);
        }
        this.attribute = attribute;
        String value = call.getString("value");
        if (value == null) {
            throw new PutAttributeOptionsException(FirebasePerformancePlugin.ERROR_VALUE_MISSING);
        }
        this.value = value;
    }

    public String getTraceName() {
        return traceName;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }
}
