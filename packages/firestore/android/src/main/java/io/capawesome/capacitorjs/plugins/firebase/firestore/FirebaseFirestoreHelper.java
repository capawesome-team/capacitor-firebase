package io.capawesome.capacitorjs.plugins.firebase.firestore;

import com.getcapacitor.JSObject;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;

public class FirebaseFirestoreHelper {

    public static HashMap<String, Object> createHashMapFromJSObject(JSObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = object.get(key);
            map.put(key, value);
        }
        return map;
    }
}