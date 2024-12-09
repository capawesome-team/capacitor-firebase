package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.FirebasePerformancePlugin;

public class GetAttributeOptions {

    private final String traceName;
    private final String attribute;

    public static class GetAttributeOptionsException extends Exception {

        public GetAttributeOptionsException(String message) {
            super(message);
        }
    }

    public GetAttributeOptions(PluginCall call) throws GetAttributeOptionsException {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw new GetAttributeOptionsException(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
        }
        this.traceName = traceName;
        String attribute = call.getString("attribute");
        if (attribute == null) {
            throw new GetAttributeOptionsException(FirebasePerformancePlugin.ERROR_ATTRIBUTE_MISSING);
        }
        this.attribute = attribute;
    }

    public String getTraceName() {
        return traceName;
    }

    public String getAttribute() {
        return attribute;
    }
}
