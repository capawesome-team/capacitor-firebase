package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.firebase.crashlytics.CustomKeysAndValues;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseCrashlytics {

    public void crash(String message) {
        throw new RuntimeException(message);
    }

    public void setCustomKey(String key, String type, PluginCall call) throws Exception {
        // NEVER use call.getInt, call.getFloat or call.getDouble here. They drop a whole number above the int range.
        Object value = call.getData().opt("value");
        switch (type) {
            case "long":
                if (!(value instanceof Integer || value instanceof Long)) {
                    throw new Exception(FirebaseCrashlyticsPlugin.ERROR_VALUE_INVALID);
                }
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).longValue());
                break;
            case "int":
                if (!(value instanceof Integer)) {
                    throw new Exception(FirebaseCrashlyticsPlugin.ERROR_VALUE_INVALID);
                }
                getFirebaseCrashlyticsInstance().setCustomKey(key, (Integer) value);
                break;
            case "boolean":
                if (!(value instanceof Boolean)) {
                    throw new Exception(FirebaseCrashlyticsPlugin.ERROR_VALUE_INVALID);
                }
                getFirebaseCrashlyticsInstance().setCustomKey(key, (Boolean) value);
                break;
            case "float":
                if (!(value instanceof Number)) {
                    throw new Exception(FirebaseCrashlyticsPlugin.ERROR_VALUE_INVALID);
                }
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).floatValue());
                break;
            case "double":
                if (!(value instanceof Number)) {
                    throw new Exception(FirebaseCrashlyticsPlugin.ERROR_VALUE_INVALID);
                }
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).doubleValue());
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

    public void recordException(String message, JSArray stacktrace, @Nullable JSArray keysAndValues) {
        Throwable throwable = getJavaScriptException(message, stacktrace);
        if (keysAndValues == null) {
            getFirebaseCrashlyticsInstance().recordException(throwable);
        } else {
            CustomKeysAndValues customKeysAndValues = getCustomKeysAndValues(keysAndValues);
            getFirebaseCrashlyticsInstance().recordException(throwable, customKeysAndValues);
        }
    }

    @NonNull
    private CustomKeysAndValues getCustomKeysAndValues(@NonNull JSArray keysAndValues) {
        CustomKeysAndValues.Builder builder = new CustomKeysAndValues.Builder();
        for (int i = 0; i < keysAndValues.length(); i++) {
            try {
                JSONObject object = keysAndValues.getJSONObject(i);
                String type = object.getString("type");
                String key = object.getString("key");
                Object value = getValueForType(type, object);

                if (value != null) {
                    addCustomKeyToBuilder(builder, key, type, value);
                }
            } catch (JSONException error) {
                System.err.println("An entry of keysAndValues was skipped: " + error.getMessage());
            }
        }

        return builder.build();
    }

    private Object getValueForType(String type, JSONObject object) throws JSONException {
        switch (type) {
            case "long":
                return object.getLong("value");
            case "int":
                return object.getInt("value");
            case "boolean":
                return object.getBoolean("value");
            case "float":
                return (float) object.getDouble("value");
            case "double":
                return object.getDouble("value");
            default:
                return object.getString("value");
        }
    }

    private void addCustomKeyToBuilder(CustomKeysAndValues.Builder builder, String key, String type, Object value) {
        if (value instanceof Long) {
            builder.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            builder.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            builder.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            builder.putFloat(key, (Float) value);
        } else if (value instanceof Double) {
            builder.putDouble(key, (Double) value);
        } else if (value instanceof String) {
            builder.putString(key, (String) value);
        }
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
