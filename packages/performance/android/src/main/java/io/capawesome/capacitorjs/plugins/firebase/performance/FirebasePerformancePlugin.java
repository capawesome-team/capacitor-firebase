package io.capawesome.capacitorjs.plugins.firebase.performance;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.perf.metrics.Trace;
import java.util.Map;

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

    @PluginMethod
    public void putAttribute(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            String attribute = call.getString("attribute");
            if (attribute == null) {
                call.reject(FirebasePerformancePlugin.ERROR_ATTRIBUTE_MISSING);
                return;
            }
            String value = call.getString("value");
            if (value == null) {
                call.reject(FirebasePerformancePlugin.ERROR_VALUE_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.putAttribute(trace, attribute, value);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getAttribute(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            String attribute = call.getString("attribute");
            if (attribute == null) {
                call.reject(FirebasePerformancePlugin.ERROR_ATTRIBUTE_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            String value = FirebasePerformance.getAttribute(trace, attribute);
            JSObject result = new JSObject();
            result.put("value", value);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
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
            Map<String, String> attributesMap = FirebasePerformance.getAttributes(trace);
            JSObject result = new JSObject();
            JSObject resultMap = new JSObject();
            for (String attribute : attributesMap.keySet()) {
                resultMap.put(attribute, attributesMap.get(attribute));
            }
            result.put("result", resultMap);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void removeAttribute(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            String attribute = call.getString("attribute");
            if (attribute == null) {
                call.reject(FirebasePerformancePlugin.ERROR_ATTRIBUTE_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.removeAttribute(trace, attribute);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void putMetric(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            String metricName = call.getString("metricName");
            if (metricName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_METRIC_NAME_MISSING);
                return;
            }
            Double num = call.getDouble("num");
            if (num == null) {
                call.reject(FirebasePerformancePlugin.ERROR_NUM_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            FirebasePerformance.putMetric(trace, metricName, (long) Math.floor(num));
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getMetric(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            String metricName = call.getString("metricName");
            if (metricName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_METRIC_NAME_MISSING);
                return;
            }
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            long value = FirebasePerformance.getMetric(trace, metricName);
            JSObject result = new JSObject();
            result.put("value", value);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void record(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }
}
