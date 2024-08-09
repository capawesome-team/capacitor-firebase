package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.AddConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.RemoveConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CapacitorPlugin(name = "FirebaseRemoteConfig")
public class FirebaseRemoteConfigPlugin extends Plugin {

    public static final String TAG = "FirebaseRemoteConfig";
    public static final String ERROR_KEY_MISSING = "key must be provided.";
    public static final String ERROR_CALLBACK_ID_MISSING = "callbackId must be provided.";


    private Map<String, PluginCall> pluginCallMap = new HashMap<>();

    private FirebaseRemoteConfig implementation;

    public void load() {
        implementation = new FirebaseRemoteConfig(this);
    }

    @PluginMethod
    public void activate(PluginCall call) {
        try {
            implementation.activate(
                new ActivateResultCallback() {
                    @Override
                    public void success(boolean success) {
                        call.resolve();
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void fetchAndActivate(PluginCall call) {
        try {
            implementation.fetchAndActivate(
                new ActivateResultCallback() {
                    @Override
                    public void success(boolean success) {
                        call.resolve();
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void fetchConfig(PluginCall call) {
        try {
            int minimumFetchIntervalInSeconds = call.getInt("minimumFetchIntervalInSeconds", 43200);
            implementation.fetchConfig(
                minimumFetchIntervalInSeconds,
                new FetchConfigResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getBoolean(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            GetValueResult<Boolean> getValueResult = implementation.getBoolean(key);
            JSObject result = new JSObject();
            result.put("value", getValueResult.value);
            result.put("source", getValueResult.source);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getNumber(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            GetValueResult<Double> getValueResult = implementation.getNumber(key);
            JSObject result = new JSObject();
            result.put("value", getValueResult.value);
            result.put("source", getValueResult.source);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getString(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            GetValueResult<String> getValueResult = implementation.getString(key);
            JSObject result = new JSObject();
            result.put("value", getValueResult.value);
            result.put("source", getValueResult.source);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setMinimumFetchInterval(PluginCall call) {
        call.reject("Not available on Android. Use setConfigSettings.");
    }

    @PluginMethod
    public void setConfigSettings(PluginCall call) {
        try {
            int fetchTimeoutInSeconds = call.getInt("fetchTimeoutInSeconds", 60);
            int minimumFetchIntervalInSeconds = call.getInt("minimumFetchIntervalInSeconds", 43200);

            Task<Void> task = implementation.setConfigSettings(fetchTimeoutInSeconds, minimumFetchIntervalInSeconds);
            task.addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            call.resolve();
                        }
                    }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addConfigUpdateListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            String callbackId = call.getCallbackId();

            this.pluginCallMap.put(callbackId, call);

            AddConfigUpdateListenerOptions options = new AddConfigUpdateListenerOptions(callbackId);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.addConfigUpdateListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void removeConfigUpdateListener(PluginCall call) {
        try {
            String callbackId = call.getString("callbackId");
            if (callbackId == null) {
                call.reject(ERROR_CALLBACK_ID_MISSING);
                return;
            }

            PluginCall savedCall = this.pluginCallMap.remove(callbackId);
            savedCall.release(this.bridge);

            RemoveConfigUpdateListenerOptions options = new RemoveConfigUpdateListenerOptions(callbackId);
            implementation.removeConfigUpdateListener(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
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

            implementation.removeAllListeners();
            super.removeAllListeners(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
