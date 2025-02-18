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

    public void recordException(String message, JSArray stacktrace, JSArray customProperties) {
        Throwable throwable = getJavaScriptException(message, stacktrace);
        CustomKeysAndValues keysAndValues = getCustomKeysAndValues(customProperties);
        getFirebaseCrashlyticsInstance().recordException(throwable, keysAndValues);
    }

    private CustomKeysAndValues getCustomKeysAndValues(JSArray keysAndValues) {
        if (keysAndValues == null) {
            return null;
        }

        CustomKeysAndValues.Builder builder = new CustomKeysAndValues.Builder();
        try {
            for (int i = 0; i < keysAndValues.length(); i++) {
                JSObject object = (JSObject) keysAndValues.get(i);
                String type = object.getString("type");
                String key = object.getString("key");
                Object value = getValueForType(type, object);

                if (value != null) {
                    addCustomKeyToBuilder(builder, key, type, value);
                }
            }
        } catch (JSONException error) {
            System.err.println("CustomProperties cannot be converted to CustomKeysAndValues! " + error.getMessage());
        }

        return builder.build();
    }

    private Object getValueForType(String type, JSObject object) throws JSONException {
        switch (type) {
            case "long":
                return Long.valueOf(object.getInt("value"));
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
