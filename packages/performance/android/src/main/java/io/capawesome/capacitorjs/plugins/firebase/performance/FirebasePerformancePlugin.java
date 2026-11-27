package io.capawesome.capacitorjs.plugins.firebase.performance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.performance.classes.CustomException;
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
import io.capawesome.capacitorjs.plugins.firebase.performance.interfaces.Result;

@CapacitorPlugin(name = "FirebasePerformance")
public class FirebasePerformancePlugin extends Plugin {

    public static final String TAG = "FirebasePerformance";

    private FirebasePerformance implementation = new FirebasePerformance();

    @PluginMethod
    public void getAttribute(PluginCall call) {
        try {
            GetAttributeOptions options = new GetAttributeOptions(call);
            NonEmptyResultCallback<GetAttributeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAttributeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getAttribute(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAttributes(PluginCall call) {
        try {
            GetAttributesOptions options = new GetAttributesOptions(call);
            NonEmptyResultCallback<GetAttributesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAttributesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getAttributes(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getMetric(PluginCall call) {
        try {
            GetMetricOptions options = new GetMetricOptions(call);
            NonEmptyResultCallback<GetMetricResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetMetricResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getMetric(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void incrementMetric(PluginCall call) {
        try {
            IncrementMetricOptions options = new IncrementMetricOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.incrementMetric(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void putAttribute(PluginCall call) {
        try {
            PutAttributeOptions options = new PutAttributeOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.putAttribute(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void putMetric(PluginCall call) {
        try {
            PutMetricOptions options = new PutMetricOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.putMetric(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void record(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void removeAttribute(PluginCall call) {
        try {
            RemoveAttributeOptions options = new RemoveAttributeOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.removeAttribute(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setEnabled(PluginCall call) {
        try {
            SetEnabledOptions options = new SetEnabledOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setEnabled(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startTrace(PluginCall call) {
        try {
            StartTraceOptions options = new StartTraceOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.startTrace(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopTrace(PluginCall call) {
        try {
            StopTraceOptions options = new StopTraceOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.stopTrace(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException ? ((CustomException) exception).getCode() : null;
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
