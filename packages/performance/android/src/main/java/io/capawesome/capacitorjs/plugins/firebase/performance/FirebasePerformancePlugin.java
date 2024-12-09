package io.capawesome.capacitorjs.plugins.firebase.performance;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.perf.metrics.Trace;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.GetAttributeOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.GetMetricOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.PutAttributeOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.PutMetricOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.RecordOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetAttributeResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetAttributesResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetMetricResult;

@CapacitorPlugin(name = "FirebasePerformance")
public class FirebasePerformancePlugin extends Plugin {

    public static final String TAG = "FirebasePerformance";
    public static final String ERROR_TRACE_NAME_MISSING = "traceName must be provided.";
    public static final String ERROR_METRIC_NAME_MISSING = "metricName must be provided.";
    public static final String ERROR_TRACE_NAME_ALREADY_ASSIGNED = "traceName already assigned.";
    public static final String ERROR_ENABLED_MISSING = "enabled must be provided.";
    public static final String ERROR_TRACE_NOT_FOUND = "No trace was found with the provided traceName.";
    public static final String ERROR_ATTRIBUTE_MISSING = "attribute must be provided.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";
    public static final String ERROR_NUM_MISSING = "num must be provided.";
    public static final String ERROR_START_TIME_MISSING = "startTime must be provided.";
    public static final String ERROR_DURATION_MISSING = "duration must be provided.";
    public static final String ERROR_INVALID_METRIC_VALUE = "provided metric value is not a number.";
    private FirebasePerformance implementation = new FirebasePerformance();

    @PluginMethod
    public void startTrace(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(ERROR_TRACE_NAME_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace != null) {
                call.reject(ERROR_TRACE_NAME_ALREADY_ASSIGNED);
                return;
            }
            implementation.startTrace(traceName);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void stopTrace(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(ERROR_TRACE_NAME_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            implementation.stopTrace(traceName);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void incrementMetric(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            String metricName = call.getString("metricName");
            Integer incrementBy = call.getInt("incrementBy", 1);
            if (traceName == null) {
                call.reject(ERROR_TRACE_NAME_MISSING);
                return;
            }
            if (metricName == null) {
                call.reject(ERROR_METRIC_NAME_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            implementation.incrementMetric(traceName, metricName, incrementBy);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setEnabled(PluginCall call) {
        try {
            Boolean enabled = call.getBoolean("enabled");
            if (enabled == null) {
                call.reject(ERROR_ENABLED_MISSING);
                return;
            }
            implementation.setEnabled(enabled);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            Boolean enabled = implementation.isEnabled();
            JSObject result = new JSObject();
            result.put("enabled", enabled);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void putAttribute(PluginCall call) {
        try {
            PutAttributeOptions options = new PutAttributeOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.putAttribute(trace, options.getAttribute(), options.getValue());
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void getAttribute(PluginCall call) {
        try {
            GetAttributeOptions options = new GetAttributeOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            GetAttributeResult result = FirebasePerformance.getAttribute(trace, options.getAttribute());
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void getAttributes(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(ERROR_TRACE_NAME_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            GetAttributesResult result = FirebasePerformance.getAttributes(trace);
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeAttribute(PluginCall call) {
        try {
            GetAttributeOptions options = new GetAttributeOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.removeAttribute(trace, options.getAttribute());
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void putMetric(PluginCall call) {
        try {
            PutMetricOptions options = new PutMetricOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.putMetric(trace, options.getMetricName(), options.getNum());
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void getMetric(PluginCall call) {
        try {
            GetMetricOptions options = new GetMetricOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            GetMetricResult result = FirebasePerformance.getMetric(trace, options.getMetricName());
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void record(PluginCall call) {
        try {
            RecordOptions options = new RecordOptions(call);
            Trace trace = implementation.getTraceByName(options.getTraceName());
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            implementation.record(
                trace,
                options.getTraceName(),
                options.getStartTime(),
                options.getDuration(),
                options.getAttributes(),
                options.getMetrics()
            );
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
