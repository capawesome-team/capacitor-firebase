package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import com.getcapacitor.JSObject;
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
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
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
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void fetchConfig(PluginCall call) {
        try {
            long minimumFetchIntervalInSeconds = call.getLong("minimumFetchIntervalInSeconds", 43200L);
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
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
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
            boolean value = implementation.getBoolean(key);
            JSObject result = new JSObject();
            result.put("value", value);
            call.resolve(result);
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
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
            double value = implementation.getNumber(key);
            JSObject result = new JSObject();
            result.put("value", value);
            call.resolve(result);
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
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
            String value = implementation.getString(key);
            JSObject result = new JSObject();
            result.put("value", value);
            call.resolve(result);
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void setMinimumFetchInterval(PluginCall call) {
        call.reject("Not available on Android.");
    }
}
