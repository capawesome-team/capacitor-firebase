package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import org.json.JSONException;

public class FirebaseCrashlytics {

    private final com.google.firebase.crashlytics.FirebaseCrashlytics crashlyticsInstance;

    FirebaseCrashlytics() {
        crashlyticsInstance = com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance();
    }

    public void crash(String message) {
        throw new RuntimeException(message);
    }

    public void setCustomKey(String key, String type, PluginCall call) {
        switch (type) {
            case "long":
                crashlyticsInstance.setCustomKey(key, Long.valueOf(call.getInt("value")));
                break;
            case "int":
                crashlyticsInstance.setCustomKey(key, call.getInt("value"));
                break;
            case "boolean":
                crashlyticsInstance.setCustomKey(key, call.getBoolean("value"));
                break;
            case "float":
                crashlyticsInstance.setCustomKey(key, call.getFloat("value"));
                break;
            case "double":
                crashlyticsInstance.setCustomKey(key, call.getDouble("value"));
                break;
            default:
                crashlyticsInstance.setCustomKey(key, call.getString("value"));
        }
    }

    public void setUserId(String userId) {
        crashlyticsInstance.setUserId(userId);
    }

    public void log(String message) {
        crashlyticsInstance.log(message);
    }

    public void setEnabled(Boolean enabled) {
        crashlyticsInstance.setCrashlyticsCollectionEnabled(enabled);
    }

    public boolean didCrashOnPreviousExecution() {
        return crashlyticsInstance.didCrashOnPreviousExecution();
    }

    public void sendUnsentReports() {
        crashlyticsInstance.sendUnsentReports();
    }

    public void deleteUnsentReports() {
        crashlyticsInstance.deleteUnsentReports();
    }

    public void recordException(String message, JSArray stacktrace) {
        Throwable throwable = getJavaScriptException(message, stacktrace);
        crashlyticsInstance.recordException(throwable);
    }

    private JavaScriptException getJavaScriptException(String message, JSArray stacktrace) {
        if (stacktrace == null) {
            return new JavaScriptException(message);
        }

        try {
            return new JavaScriptException(message, stacktrace);
        } catch (JSONException error) {
            System.err.println("Stacktrace is not parsable! " + error.getMessage());
            return new JavaScriptException(message);
        }
    }
}
