package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseRemoteConfig")
public class FirebaseRemoteConfigPlugin extends Plugin {

    public static final String TAG = "FirebaseRemoteConfig";
    public static final String ERROR_KEY_MISSING = "key must be provided.";
    private FirebaseRemoteConfig implementation = new FirebaseRemoteConfig();

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
            call.reject(exception.getLocalizedMessage());
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
            call.reject(exception.getLocalizedMessage());
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
            call.reject(exception.getLocalizedMessage());
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
            call.reject(exception.getLocalizedMessage());
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
            call.reject(exception.getLocalizedMessage());
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
            call.reject(exception.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void setMinimumFetchInterval(PluginCall call) {
        call.reject("Not available on Android.");
    }
}
