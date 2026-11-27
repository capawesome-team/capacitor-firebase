package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;

public class GetMetricOptions {

    @NonNull
    private final String metricName;

    @NonNull
    private final String traceName;

    public GetMetricOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the traceName before the metricName, so a missing traceName is reported first.
        this.traceName = GetMetricOptions.getTraceNameFromCall(call);
        this.metricName = GetMetricOptions.getMetricNameFromCall(call);
    }

    @NonNull
    public String getMetricName() {
        return metricName;
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

    @NonNull
    private static String getTraceNameFromCall(@NonNull PluginCall call) throws Exception {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw CustomExceptions.TRACE_NAME_MISSING;
        }
        return traceName;
    }
}
