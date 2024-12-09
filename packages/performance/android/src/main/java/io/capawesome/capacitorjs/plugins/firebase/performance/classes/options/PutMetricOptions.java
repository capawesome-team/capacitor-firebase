package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.FirebasePerformancePlugin;

public class PutMetricOptions {

    private final String traceName;
    private final String metricName;
    private final long num;

    public static class PutMetricOptionsException extends Exception {

        public PutMetricOptionsException(String message) {
            super(message);
        }
    }

    public PutMetricOptions(PluginCall call) throws PutMetricOptionsException {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw new PutMetricOptionsException(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
        }
        this.traceName = traceName;
        String metricName = call.getString("metricName");
        if (metricName == null) {
            throw new PutMetricOptionsException(FirebasePerformancePlugin.ERROR_METRIC_NAME_MISSING);
        }
        this.metricName = metricName;
        Double num = call.getDouble("num");
        if (num == null) {
            throw new PutMetricOptionsException(FirebasePerformancePlugin.ERROR_NUM_MISSING);
        }
        this.num = (long) Math.floor(num);
    }

    public String getTraceName() {
        return traceName;
    }

    public String getMetricName() {
        return metricName;
    }

    public long getNum() {
        return num;
    }
}
