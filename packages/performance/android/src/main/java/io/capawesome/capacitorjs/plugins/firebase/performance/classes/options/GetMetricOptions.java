package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.FirebasePerformancePlugin;

public class GetMetricOptions {

    private final String traceName;
    private final String metricName;

    public static class GetMetricOptionsException extends Exception {

        public GetMetricOptionsException(String message) {
            super(message);
        }
    }

    public GetMetricOptions(PluginCall call) throws GetMetricOptionsException {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw new GetMetricOptionsException(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
        }
        this.traceName = traceName;
        String metricName = call.getString("metricName");
        if (metricName == null) {
            throw new GetMetricOptionsException(FirebasePerformancePlugin.ERROR_METRIC_NAME_MISSING);
        }
        this.metricName = metricName;
    }

    public String getTraceName() {
        return traceName;
    }

    public String getMetricName() {
        return metricName;
    }
}
