package app.capgo.capacitor.firebase.functions.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.functions.interfaces.Result;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.ArrayList;
import java.util.Map;

public class CallResult implements Result {

    @Nullable
    private Object data;

    public CallResult(@Nullable Object data) {
        this.data = data;
    }

    @NonNull
    public JSObject toJSObject() {
        Object dataResult = CallResult.createJSValue(data);

        JSObject result = new JSObject();
        result.put("data", dataResult);
        return result;
    }

    @Nullable
    private static Object createJSValue(@Nullable Object object) {
        if (object instanceof Map) {
            return createJSObjectFromMap((Map<String, Object>) object);
        } else if (object instanceof ArrayList) {
            return createJSArrayFromArrayList((ArrayList) object);
        }
        return object;
    }

    @Nullable
    private static JSObject createJSObjectFromMap(@Nullable Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        JSObject object = new JSObject();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof ArrayList) {
                value = createJSArrayFromArrayList((ArrayList) value);
            } else if (value instanceof Map) {
                value = createJSObjectFromMap((Map<String, Object>) value);
            }
            object.put(key, value);
        }
        return object;
    }

    private static JSArray createJSArrayFromArrayList(ArrayList arrayList) {
        JSArray array = new JSArray();
        for (Object value : arrayList) {
            if (value instanceof Map) {
                value = createJSObjectFromMap((Map<String, Object>) value);
            } else if (value instanceof ArrayList) {
                value = createJSArrayFromArrayList((ArrayList) value);
            }
            array.put(value);
        }
        return array;
    }
}
