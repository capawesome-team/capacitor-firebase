package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import org.json.JSONException;

public class FirebaseCrashlytics {

    private static final String KEY_CRASHLYTICS_AUTO_COLLECTION_ENABLED = "crashlytics_auto_collection_enabled";
    private static final String KEY_CRASHLYTICS_SHARED_PREFS = "com.google.firebase.crashlytics";

    private final com.google.firebase.crashlytics.FirebaseCrashlytics crashlyticsInstance;
    private final FirebaseCrashlyticsPlugin plugin;

    FirebaseCrashlytics(FirebaseCrashlyticsPlugin plugin) {
        this.plugin = plugin;
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

    public boolean isEnabled() {
        boolean enabled;
        SharedPreferences sharedPreferences = this.getFirebaseCrashlyticsSharedPreferences();
        if (sharedPreferences.contains(FirebaseCrashlytics.KEY_CRASHLYTICS_AUTO_COLLECTION_ENABLED)) {
            enabled = sharedPreferences.getBoolean(FirebaseCrashlytics.KEY_CRASHLYTICS_AUTO_COLLECTION_ENABLED, true);
        } else {
            Bundle metaData = this.getApplicationMetaData();
            enabled = metaData.getBoolean(FirebaseCrashlytics.KEY_CRASHLYTICS_AUTO_COLLECTION_ENABLED, true);
        }
        return enabled;
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

    private SharedPreferences getFirebaseCrashlyticsSharedPreferences() {
        Context context = this.plugin.getActivity().getApplicationContext();
        return context.getSharedPreferences(FirebaseCrashlytics.KEY_CRASHLYTICS_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    @Nullable
    private Bundle getApplicationMetaData() {
        Context context = this.plugin.getActivity().getApplicationContext();
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException exception) {
            // Ignore
        }
        return null;
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
