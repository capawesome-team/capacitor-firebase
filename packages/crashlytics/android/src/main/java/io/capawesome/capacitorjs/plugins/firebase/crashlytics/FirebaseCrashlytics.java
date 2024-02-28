package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import org.json.JSONException;

public class FirebaseCrashlytics {

    public void crash(String message) {
        throw new RuntimeException(message);
    }

    public void setCustomKey(String key, String type, PluginCall call) {
        switch (type) {
            case "long":
                getFirebaseCrashlyticsInstance().setCustomKey(key, Long.valueOf(call.getInt("value")));
                break;
            case "int":
                getFirebaseCrashlyticsInstance().setCustomKey(key, call.getInt("value"));
                break;
            case "boolean":
                getFirebaseCrashlyticsInstance().setCustomKey(key, call.getBoolean("value"));
                break;
            case "float":
                getFirebaseCrashlyticsInstance().setCustomKey(key, call.getFloat("value"));
                break;
            case "double":
                getFirebaseCrashlyticsInstance().setCustomKey(key, call.getDouble("value"));
                break;
            default:
                getFirebaseCrashlyticsInstance().setCustomKey(key, call.getString("value"));
        }
    }

    public void setUserId(String userId) {
        getFirebaseCrashlyticsInstance().setUserId(userId);
    }

    public void log(String message) {
        getFirebaseCrashlyticsInstance().log(message);
    }

    public void setEnabled(Boolean enabled) {
        getFirebaseCrashlyticsInstance().setCrashlyticsCollectionEnabled(enabled);
    }

    public boolean didCrashOnPreviousExecution() {
        return getFirebaseCrashlyticsInstance().didCrashOnPreviousExecution();
    }

    public void sendUnsentReports() {
        getFirebaseCrashlyticsInstance().sendUnsentReports();
    }

    public void deleteUnsentReports() {
        getFirebaseCrashlyticsInstance().deleteUnsentReports();
    }

    public void recordException(String message, JSArray stacktrace) {
        Throwable throwable = getJavaScriptException(message, stacktrace);
        getFirebaseCrashlyticsInstance().recordException(throwable);
    }

    private com.google.firebase.crashlytics.FirebaseCrashlytics getFirebaseCrashlyticsInstance() {
        return com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance();
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
