package io.capawesome.capacitorjs.plugins.firebase.performance;

import com.google.firebase.perf.metrics.Trace;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FirebasePerformance {

    private HashMap<String, Trace> traces = new HashMap<String, Trace>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startTrace(String traceName) {
        Trace trace = this.getFirebasePerformanceInstance().newTrace(traceName);
        trace.start();
        this.traces.put(traceName, trace);
    }

    public void stopTrace(String traceName) {
        Trace trace = this.traces.get(traceName);
        trace.stop();
        this.traces.remove(traceName);
    }

    public void incrementMetric(String traceName, String metricName, Integer incrementBy) {
        Trace trace = this.traces.get(traceName);
        trace.incrementMetric(metricName, incrementBy);
    }

    public Trace getTraceByName(String traceName) {
        if (this.traces.containsKey(traceName)) {
            return this.traces.get(traceName);
        }
        return null;
    }

    public void setEnabled(Boolean enabled) {
        this.getFirebasePerformanceInstance().setPerformanceCollectionEnabled(enabled);
    }

    public Boolean isEnabled() {
        return this.getFirebasePerformanceInstance().isPerformanceCollectionEnabled();
    }

    public static void putAttribute(Trace trace, String attribute, String value) {
        trace.putAttribute(attribute, value);
    }

    public static String getAttribute(Trace trace, String attribute) {
        return trace.getAttribute(attribute);
    }

    public static Map<String, String> getAttributes(Trace trace) {
        return trace.getAttributes();
    }

    public static void removeAttribute(Trace trace, String attribute) {
        trace.removeAttribute(attribute);
    }

    public static void putMetric(Trace trace, String metricName, long num) {
        trace.putMetric(metricName, num);
    }

    public static long getMetric(Trace trace, String metricName) {
        return trace.getLongMetric(metricName);
    }

    public void record(
        Trace trace,
        String traceName,
        long startTime,
        long duration,
        Map<String, String> attributes,
        Map<String, Long> metrics
    ) {
        long currentTime = System.currentTimeMillis();
        long startDelay = Math.max(0, (startTime - currentTime));
        if (attributes != null) {
            for (String key : attributes.keySet()) {
                FirebasePerformance.putAttribute(trace, key, attributes.get(key));
            }
        }
        if (metrics != null) {
            for (String key : metrics.keySet()) {
                FirebasePerformance.putMetric(trace, key, metrics.get(key));
            }
        }
        this.scheduler.schedule(
                () -> {
                    this.startTrace(traceName);
                    scheduler.schedule(() -> this.stopTrace(traceName), duration, TimeUnit.MILLISECONDS);
                },
                startDelay,
                TimeUnit.MILLISECONDS
            );
    }

    private com.google.firebase.perf.FirebasePerformance getFirebasePerformanceInstance() {
        return com.google.firebase.perf.FirebasePerformance.getInstance();
    }
}
