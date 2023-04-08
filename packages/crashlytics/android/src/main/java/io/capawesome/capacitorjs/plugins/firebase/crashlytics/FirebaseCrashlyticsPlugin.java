package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseCrashlytics")
public class FirebaseCrashlyticsPlugin extends Plugin {

    public static final String TAG = "FirebaseCrashlytics";
    public static final String ERROR_MESSAGE_MISSING = "message must be provided.";
    public static final String ERROR_KEY_MISSING = "key must be provided.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";
    public static final String ERROR_USERID_MISSING = "userId must be provided.";
    public static final String ERROR_ENABLED_MISSING = "enabled must be provided.";
    private FirebaseCrashlytics implementation;

    @Override
    public void load() {
        implementation = new FirebaseCrashlytics();
    }

    @PluginMethod
    public void crash(PluginCall call) {
        String message = call.getString("message");
        if (message == null) {
            call.reject(ERROR_MESSAGE_MISSING);
            return;
        }
        call.resolve();
        implementation.crash(message);
    }

    @PluginMethod
    public void setCustomKey(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            boolean hasValue = call.hasOption("value");
            if (!hasValue) {
                call.reject(ERROR_VALUE_MISSING);
                return;
            }
            String type = call.getString("type", "string");
            implementation.setCustomKey(key, type, call);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setUserId(PluginCall call) {
        try {
            String userId = call.getString("userId");
            if (userId == null) {
                call.reject(ERROR_USERID_MISSING);
                return;
            }
            implementation.setUserId(userId);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void log(PluginCall call) {
        try {
            String message = call.getString("message");
            if (message == null) {
                call.reject(ERROR_MESSAGE_MISSING);
                return;
            }
            implementation.log(message);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setEnabled(PluginCall call) {
        try {
            Boolean enabled = call.getBoolean("enabled");
            if (enabled == null) {
                call.reject(ERROR_ENABLED_MISSING);
                return;
            }
            implementation.setEnabled(enabled);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }

    @PluginMethod
    public void didCrashOnPreviousExecution(PluginCall call) {
        try {
            boolean crashed = implementation.didCrashOnPreviousExecution();
            JSObject ret = new JSObject();
            ret.put("crashed", crashed);
            call.resolve(ret);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void sendUnsentReports(PluginCall call) {
        try {
            implementation.sendUnsentReports();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void deleteUnsentReports(PluginCall call) {
        try {
            implementation.deleteUnsentReports();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void recordException(PluginCall call) {
        try {
            String message = call.getString("message");
            if (message == null) {
                call.reject(ERROR_MESSAGE_MISSING);
                return;
            }

            JSArray stacktrace = call.getArray("stacktrace", null);
            implementation.recordException(message, stacktrace);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
