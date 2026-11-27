package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class IncrementMetricOptions {

    private final int incrementBy;

    @NonNull
    private final String metricName;

    @NonNull
    private final String traceName;

    public IncrementMetricOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the traceName before the metricName, so a missing traceName is reported first.
        this.traceName = IncrementMetricOptions.getTraceNameFromCall(call);
        this.metricName = IncrementMetricOptions.getMetricNameFromCall(call);
        this.incrementBy = IncrementMetricOptions.getIncrementByFromCall(call);
    }

    public int getIncrementBy() {
        return incrementBy;
    }

    @NonNull
    public String getMetricName() {
        return metricName;
    }

    @NonNull
    public String getTraceName() {
        return traceName;
    }

    private static int getIncrementByFromCall(@NonNull PluginCall call) {
        return call.getInt("incrementBy", 1);
    }

    @NonNull
    private static String getMetricNameFromCall(@NonNull PluginCall call) throws Exception {
        String metricName = call.getString("metricName");
        if (metricName == null) {
            throw CustomExceptions.METRIC_NAME_MISSING;
        }
        return metricName;
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
