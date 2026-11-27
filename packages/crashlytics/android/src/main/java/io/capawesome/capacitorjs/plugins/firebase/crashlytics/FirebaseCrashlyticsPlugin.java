package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.CrashOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.LogOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.RecordExceptionOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetCustomKeyOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetUserIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.results.DidCrashOnPreviousExecutionResult;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.Result;

@CapacitorPlugin(name = "FirebaseCrashlytics")
public class FirebaseCrashlyticsPlugin extends Plugin {

    public static final String TAG = "FirebaseCrashlytics";
    private FirebaseCrashlytics implementation;

    @PluginMethod
    public void crash(PluginCall call) {
        CrashOptions options;
        try {
            options = new CrashOptions(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
            return;
        }
        resolveCall(call);
        // NEVER wrap this line in a try. The exception must reach Capacitor so that the app crashes.
        implementation.crash(options);
    }

    @PluginMethod
    public void deleteUnsentReports(PluginCall call) {
        try {
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

            implementation.deleteUnsentReports(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void didCrashOnPreviousExecution(PluginCall call) {
        try {
            NonEmptyResultCallback<DidCrashOnPreviousExecutionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull DidCrashOnPreviousExecutionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.didCrashOnPreviousExecution(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @Override
    public void load() {
        implementation = new FirebaseCrashlytics();
    }

    @PluginMethod
    public void log(PluginCall call) {
        try {
            LogOptions options = new LogOptions(call);
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

            implementation.log(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void recordException(PluginCall call) {
        try {
            RecordExceptionOptions options = new RecordExceptionOptions(call);
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

            implementation.recordException(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sendUnsentReports(PluginCall call) {
        try {
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

            implementation.sendUnsentReports(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCustomKey(PluginCall call) {
        try {
            SetCustomKeyOptions options = new SetCustomKeyOptions(call);
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

            implementation.setCustomKey(options, callback);
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
    public void setUserId(PluginCall call) {
        try {
            SetUserIdOptions options = new SetUserIdOptions(call);
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

            implementation.setUserId(options, callback);
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
