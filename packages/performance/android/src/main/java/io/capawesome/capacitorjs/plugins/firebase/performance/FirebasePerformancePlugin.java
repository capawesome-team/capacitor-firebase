package io.capawesome.capacitorjs.plugins.firebase.performance;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.perf.metrics.Trace;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

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

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
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

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
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

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
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

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
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

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void record(PluginCall call) {
        try {
            String traceName = call.getString("traceName");
            if (traceName == null) {
                call.reject(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
                return;
            }
            Long startTime = call.getLong("startTime");
            if (startTime == null) {
                call.reject(FirebasePerformancePlugin.ERROR_START_TIME_MISSING);
                return;
            }
            Long duration = call.getLong("duration");
            if (duration == null) {
                call.reject(FirebasePerformancePlugin.ERROR_DURATION_MISSING);
                return;
            }
            JSObject metrics = call.getObject("metrics", new JSObject());
            Map<String, Long> mappedMetrics = jsObjectToMetricsMap(metrics == null ? new JSObject() : metrics);
            JSObject attributes = call.getObject("attributes", new JSObject());
            Map<String, String> mappedAttributes = jsObjectToAttributesMap(attributes == null ? new JSObject() : attributes);
            Trace trace = implementation.getTraceByName(traceName);
            if (trace == null) {
                call.reject(ERROR_TRACE_NOT_FOUND);
                return;
            }
            implementation.record(trace, traceName, startTime, duration, mappedAttributes, mappedMetrics);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    private static Map<String, String> jsObjectToAttributesMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, object.get(key).toString());
        }
        return map;
    }

    private static Map<String, Long> jsObjectToMetricsMap(JSONObject object) throws JSONException {
        Map<String, Long> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (object.get(key) instanceof Long) {
                map.put(key, (Long) object.get(key));
            } else if (object.get(key) instanceof Double) {
                map.put(key, (long) Math.floor((double) object.get(key)));
            } else {
                throw new JSONException(FirebasePerformancePlugin.ERROR_INVALID_METRIC_VALUE);
            }
        }
        return map;
    }
}
