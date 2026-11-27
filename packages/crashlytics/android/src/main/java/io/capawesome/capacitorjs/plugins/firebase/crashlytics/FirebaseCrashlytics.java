package io.capawesome.capacitorjs.plugins.firebase.crashlytics;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.google.firebase.crashlytics.CustomKeysAndValues;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.CrashOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.LogOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.RecordExceptionOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetCustomKeyOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options.SetUserIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.results.DidCrashOnPreviousExecutionResult;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.NonEmptyResultCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseCrashlytics {

    public void crash(@NonNull CrashOptions options) {
        throw new RuntimeException(options.getMessage());
    }

    public void deleteUnsentReports(@NonNull EmptyResultCallback callback) {
        getFirebaseCrashlyticsInstance().deleteUnsentReports();
        callback.success();
    }

    public void didCrashOnPreviousExecution(@NonNull NonEmptyResultCallback<DidCrashOnPreviousExecutionResult> callback) {
        boolean crashed = getFirebaseCrashlyticsInstance().didCrashOnPreviousExecution();
        DidCrashOnPreviousExecutionResult result = new DidCrashOnPreviousExecutionResult(crashed);
        callback.success(result);
    }

    public void log(@NonNull LogOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseCrashlyticsInstance().log(options.getMessage());
        callback.success();
    }

    public void recordException(@NonNull RecordExceptionOptions options, @NonNull EmptyResultCallback callback) {
        Throwable throwable = getJavaScriptException(options.getMessage(), options.getStacktrace());
        JSArray keysAndValues = options.getKeysAndValues();
        if (keysAndValues == null) {
            getFirebaseCrashlyticsInstance().recordException(throwable);
        } else {
            CustomKeysAndValues customKeysAndValues = getCustomKeysAndValues(keysAndValues);
            getFirebaseCrashlyticsInstance().recordException(throwable, customKeysAndValues);
        }
        callback.success();
    }

    public void sendUnsentReports(@NonNull EmptyResultCallback callback) {
        getFirebaseCrashlyticsInstance().sendUnsentReports();
        callback.success();
    }

    public void setCustomKey(@NonNull SetCustomKeyOptions options, @NonNull EmptyResultCallback callback) {
        String key = options.getKey();
        Object value = options.getValue();
        switch (options.getType()) {
            case "long":
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).longValue());
                break;
            case "int":
                getFirebaseCrashlyticsInstance().setCustomKey(key, (Integer) value);
                break;
            case "boolean":
                getFirebaseCrashlyticsInstance().setCustomKey(key, (Boolean) value);
                break;
            case "float":
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).floatValue());
                break;
            case "double":
                getFirebaseCrashlyticsInstance().setCustomKey(key, ((Number) value).doubleValue());
                break;
            default:
                getFirebaseCrashlyticsInstance().setCustomKey(key, (String) value);
        }
        callback.success();
    }

    public void setEnabled(@NonNull SetEnabledOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseCrashlyticsInstance().setCrashlyticsCollectionEnabled(options.getEnabled());
        callback.success();
    }

    public void setUserId(@NonNull SetUserIdOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseCrashlyticsInstance().setUserId(options.getUserId());
        callback.success();
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
}
