package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.events.AddConfigUpdateListenerOptionsCallbackEvent;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.AddConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.FetchConfigOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.GetOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.RemoveConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetCustomSignalsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetDefaultsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetSettingsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetAllResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetBooleanResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetNumberResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetStringResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CapacitorPlugin(name = "FirebaseRemoteConfig")
public class FirebaseRemoteConfigPlugin extends Plugin {

    public static final String TAG = "FirebaseRemoteConfig";

    private FirebaseRemoteConfig implementation;

    private Map<String, PluginCall> pluginCallMap = new HashMap<>();

    @PluginMethod
    public void activate(PluginCall call) {
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

            implementation.activate(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addConfigUpdateListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            AddConfigUpdateListenerOptions options = new AddConfigUpdateListenerOptions(call);

            this.pluginCallMap.put(options.getCallbackId(), call);

            NonEmptyResultCallback<AddConfigUpdateListenerOptionsCallbackEvent> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull AddConfigUpdateListenerOptionsCallbackEvent result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.addConfigUpdateListener(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void fetchAndActivate(PluginCall call) {
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

            implementation.fetchAndActivate(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void fetchConfig(PluginCall call) {
        try {
            FetchConfigOptions options = new FetchConfigOptions(call);
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

            implementation.fetchConfig(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAll(PluginCall call) {
        try {
            NonEmptyResultCallback<GetAllResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAllResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getAll(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getBoolean(PluginCall call) {
        try {
            GetOptions options = new GetOptions(call);
            NonEmptyResultCallback<GetBooleanResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetBooleanResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getBoolean(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getInfo(PluginCall call) {
        try {
            NonEmptyResultCallback<GetInfoResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetInfoResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getInfo(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getNumber(PluginCall call) {
        try {
            GetOptions options = new GetOptions(call);
            NonEmptyResultCallback<GetNumberResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetNumberResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getNumber(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getString(PluginCall call) {
        try {
            GetOptions options = new GetOptions(call);
            NonEmptyResultCallback<GetStringResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetStringResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getString(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void load() {
        implementation = new FirebaseRemoteConfig(this);
    }

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        try {
            Iterator<Map.Entry<String, PluginCall>> iterator = this.pluginCallMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, PluginCall> entry = iterator.next();
                PluginCall savedCall = entry.getValue();
                savedCall.release(this.bridge);
                iterator.remove();
            }

            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    FirebaseRemoteConfigPlugin.super.removeAllListeners(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.removeAllListeners(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeConfigUpdateListener(PluginCall call) {
        try {
            RemoveConfigUpdateListenerOptions options = new RemoveConfigUpdateListenerOptions(call);

            PluginCall savedCall = this.pluginCallMap.remove(options.getCallbackId());
            if (savedCall != null) {
                savedCall.release(this.bridge);
            }

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

            implementation.removeConfigUpdateListener(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCustomSignals(PluginCall call) {
        try {
            SetCustomSignalsOptions options = new SetCustomSignalsOptions(call);
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

            implementation.setCustomSignals(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setDefaults(PluginCall call) {
        try {
            SetDefaultsOptions options = new SetDefaultsOptions(call);
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

            implementation.setDefaults(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setMinimumFetchInterval(PluginCall call) {
        rejectCall(call, CustomExceptions.NOT_AVAILABLE);
    }

    @PluginMethod
    public void setSettings(PluginCall call) {
        try {
            SetSettingsOptions options = new SetSettingsOptions(call);
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

            implementation.setSettings(options, callback);
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
