package io.capawesome.capacitorjs.plugins.firebase.performance;

import androidx.annotation.NonNull;
import com.google.firebase.perf.metrics.Trace;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.GetAttributeOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.GetAttributesOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.GetMetricOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.IncrementMetricOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.PutAttributeOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.PutMetricOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.RemoveAttributeOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.SetEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.StartTraceOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.options.StopTraceOptions;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetAttributeResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetAttributesResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.GetMetricResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.NonEmptyResultCallback;
import java.util.HashMap;
import java.util.Map;

public class FirebasePerformance {

    private HashMap<String, Trace> traces = new HashMap<String, Trace>();

    public void getAttribute(@NonNull GetAttributeOptions options, @NonNull NonEmptyResultCallback<GetAttributeResult> callback)
        throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        String value = trace.getAttribute(options.getAttribute());
        GetAttributeResult result = new GetAttributeResult(value);
        callback.success(result);
    }

    public void getAttributes(@NonNull GetAttributesOptions options, @NonNull NonEmptyResultCallback<GetAttributesResult> callback)
        throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        Map<String, String> attributes = trace.getAttributes();
        GetAttributesResult result = new GetAttributesResult(attributes);
        callback.success(result);
    }

    public void getMetric(@NonNull GetMetricOptions options, @NonNull NonEmptyResultCallback<GetMetricResult> callback) throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        long value = trace.getLongMetric(options.getMetricName());
        GetMetricResult result = new GetMetricResult(value);
        callback.success(result);
    }

    public void incrementMetric(@NonNull IncrementMetricOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        trace.incrementMetric(options.getMetricName(), options.getIncrementBy());
        callback.success();
    }

    public void isEnabled(@NonNull NonEmptyResultCallback<IsEnabledResult> callback) {
        boolean enabled = this.getFirebasePerformanceInstance().isPerformanceCollectionEnabled();
        IsEnabledResult result = new IsEnabledResult(enabled);
        callback.success(result);
    }

    public void putAttribute(@NonNull PutAttributeOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        trace.putAttribute(options.getAttribute(), options.getValue());
        callback.success();
    }

    public void putMetric(@NonNull PutMetricOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        trace.putMetric(options.getMetricName(), (long) Math.floor(options.getNum()));
        callback.success();
    }

    public void removeAttribute(@NonNull RemoveAttributeOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        Trace trace = this.getTraceByName(options.getTraceName());
        trace.removeAttribute(options.getAttribute());
        callback.success();
    }

    public void setEnabled(@NonNull SetEnabledOptions options, @NonNull EmptyResultCallback callback) {
        this.getFirebasePerformanceInstance().setPerformanceCollectionEnabled(options.getEnabled());
        callback.success();
    }

    public void startTrace(@NonNull StartTraceOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        String traceName = options.getTraceName();
        if (this.traces.containsKey(traceName)) {
            throw CustomExceptions.TRACE_NAME_ALREADY_ASSIGNED;
        }
        Trace trace = this.getFirebasePerformanceInstance().newTrace(traceName);
        trace.start();
        this.traces.put(traceName, trace);
        callback.success();
    }

    public void stopTrace(@NonNull StopTraceOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        String traceName = options.getTraceName();
        Trace trace = this.getTraceByName(traceName);
        trace.stop();
        this.traces.remove(traceName);
        callback.success();
    }

    private com.google.firebase.perf.FirebasePerformance getFirebasePerformanceInstance() {
        return com.google.firebase.perf.FirebasePerformance.getInstance();
    }

    @NonNull
    private Trace getTraceByName(@NonNull String traceName) throws Exception {
        Trace trace = this.traces.get(traceName);
        if (trace == null) {
            throw CustomExceptions.TRACE_NOT_FOUND;
        }
        return trace;
    }
}
