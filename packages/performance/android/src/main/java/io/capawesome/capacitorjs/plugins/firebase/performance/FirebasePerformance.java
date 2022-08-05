package io.capawesome.capacitorjs.plugins.firebase.performance;

import com.google.firebase.perf.metrics.Trace;
import java.util.HashMap;

public class FirebasePerformance {

    private HashMap<String, Trace> traces = new HashMap<String, Trace>();

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

    public void setPerformanceCollectionEnabled(Boolean enabled) {
        this.getFirebasePerformanceInstance().setPerformanceCollectionEnabled(enabled);
    }

    public Boolean isPerformanceCollectionEnabled() {
        return this.getFirebasePerformanceInstance().isPerformanceCollectionEnabled();
    }

    private com.google.firebase.perf.FirebasePerformance getFirebasePerformanceInstance() {
        return com.google.firebase.perf.FirebasePerformance.getInstance();
    }
}
