package io.capawesome.capacitorjs.plugins.firebase.performance.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.performance.FirebasePerformancePlugin;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordOptions {

    private final String traceName;
    private final long startTime;
    private final long duration;
    private final Map<String, Long> metrics;
    private final Map<String, String> attributes;

    public static class RecordOptionsException extends Exception {

        public RecordOptionsException(String message) {
            super(message);
        }
    }

    public RecordOptions(PluginCall call) throws RecordOptionsException, JSONException {
        String traceName = call.getString("traceName");
        if (traceName == null) {
            throw new RecordOptionsException(FirebasePerformancePlugin.ERROR_TRACE_NAME_MISSING);
        }
        this.traceName = traceName;
        Long startTime = call.getLong("startTime");
        if (startTime == null) {
            throw new RecordOptionsException(FirebasePerformancePlugin.ERROR_START_TIME_MISSING);
        }
        this.startTime = startTime;
        Long duration = call.getLong("duration");
        if (duration == null) {
            throw new RecordOptionsException(FirebasePerformancePlugin.ERROR_DURATION_MISSING);
        }
        this.duration = duration;
        JSObject metrics = call.getObject("metrics", new JSObject());
        if (metrics == null) {
            this.metrics = null;
        } else {
            this.metrics = jsObjectToMetricsMap(metrics);
        }
        JSObject attributes = call.getObject("attributes", new JSObject());
        if (attributes == null) {
            this.attributes = null;
        } else {
            this.attributes = jsObjectToAttributesMap(attributes);
        }
    }

    public String getTraceName() {
        return traceName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    @Nullable
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Nullable
    public Map<String, Long> getMetrics() {
        return metrics;
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
