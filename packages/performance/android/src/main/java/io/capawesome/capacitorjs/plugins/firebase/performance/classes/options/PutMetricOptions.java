package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class PutMetricOptions {

    @NonNull
    private final String metricName;

    private final double num;

    @NonNull
    private final String traceName;

    public PutMetricOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the traceName, the metricName and the num in this order, so the first missing one is reported.
        this.traceName = PutMetricOptions.getTraceNameFromCall(call);
        this.metricName = PutMetricOptions.getMetricNameFromCall(call);
        this.num = PutMetricOptions.getNumFromCall(call);
    }

    @NonNull
    public String getMetricName() {
        return metricName;
    }

    public double getNum() {
        return num;
    }

    @NonNull
    public String getTraceName() {
        return traceName;
    }

    @NonNull
    private static String getMetricNameFromCall(@NonNull PluginCall call) throws Exception {
        String metricName = call.getString("metricName");
        if (metricName == null) {
            throw CustomExceptions.METRIC_NAME_MISSING;
        }
        return metricName;
    }

    private static double getNumFromCall(@NonNull PluginCall call) throws Exception {
        // NEVER use call.getDouble here. It drops a whole number above the int range, which arrives as a Long.
        Object num = call.getData().opt("num");
        if (!(num instanceof Number)) {
            throw CustomExceptions.NUM_MISSING;
        }
        return ((Number) num).doubleValue();
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
