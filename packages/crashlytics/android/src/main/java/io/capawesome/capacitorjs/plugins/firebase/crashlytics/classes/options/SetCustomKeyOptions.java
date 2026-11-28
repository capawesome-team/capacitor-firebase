package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomExceptions;

public class SetCustomKeyOptions {

    @NonNull
    private final String key;

    @NonNull
    private final String type;

    @NonNull
    private final Object value;

    public SetCustomKeyOptions(@NonNull PluginCall call) throws Exception {
        this.key = SetCustomKeyOptions.getKeyFromCall(call);
        this.type = SetCustomKeyOptions.getTypeFromCall(call);
        this.value = SetCustomKeyOptions.getValueFromCall(call, this.type);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public Object getValue() {
        return value;
    }

    @NonNull
    private static String getKeyFromCall(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        return key;
    }

    @NonNull
    private static String getTypeFromCall(@NonNull PluginCall call) {
        return call.getString("type", "string");
    }

    @NonNull
    private static Object getValueFromCall(@NonNull PluginCall call, @NonNull String type) throws Exception {
        if (call.getData().isNull("value")) {
            throw CustomExceptions.VALUE_MISSING;
        }
        // NEVER use call.getInt, call.getFloat or call.getDouble here. They drop a whole number above the int range.
        Object value = call.getData().opt("value");
        switch (type) {
            case "long":
                if (!(value instanceof Integer || value instanceof Long)) {
                    throw CustomExceptions.VALUE_INVALID;
                }
                return value;
            case "int":
                if (!(value instanceof Integer)) {
                    throw CustomExceptions.VALUE_INVALID;
                }
                return value;
            case "boolean":
                if (!(value instanceof Boolean)) {
                    throw CustomExceptions.VALUE_INVALID;
                }
                return value;
            case "float":
            case "double":
                if (!(value instanceof Number)) {
                    throw CustomExceptions.VALUE_INVALID;
                }
                return value;
            default:
                if (!(value instanceof String)) {
                    throw CustomExceptions.VALUE_INVALID;
                }
                return value;
        }
    }
}
