package io.capawesome.capacitorjs.plugins.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryEndAtConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryLimitConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryOrderByConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryStartAtConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;

public class FirebaseFirestoreHelper {

    public static HashMap<String, Object> createHashMapFromJSObject(JSObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = object.get(key);
            if (value.toString().equals("null")) {
                map.put(key, null);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    public static JSObject createJSObjectFromMap(Map<String, Object> map) {
        JSObject object = new JSObject();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            object.put(key, value);
        }
        return object;
    }

    @Nullable
    public static QueryCompositeFilterConstraint createQueryCompositeFilterConstraintFromJSObject(@Nullable JSObject compositeFilter)
        throws JSONException {
        if (compositeFilter == null) {
            return null;
        } else {
            return new QueryCompositeFilterConstraint(compositeFilter);
        }
    }

    @NonNull
    public static QueryNonFilterConstraint[] createQueryNonFilterConstraintArrayFromJSArray(@Nullable JSArray queryConstraints)
        throws JSONException {
        if (queryConstraints == null) {
            return new QueryNonFilterConstraint[0];
        } else {
            QueryNonFilterConstraint[] queryNonFilterConstraint = new QueryNonFilterConstraint[queryConstraints.length()];
            for (int i = 0; i < queryConstraints.length(); i++) {
                JSObject queryConstraint = JSObject.fromJSONObject(queryConstraints.getJSONObject(i));
                String queryConstraintType = queryConstraint.getString("type");
                switch (queryConstraintType) {
                    case "orderBy":
                        queryNonFilterConstraint[i] = new QueryOrderByConstraint(queryConstraint);
                        break;
                    case "limit":
                        queryNonFilterConstraint[i] = new QueryLimitConstraint(queryConstraint);
                        break;
                    case "startAt":
                    case "startAfter":
                        queryNonFilterConstraint[i] = new QueryStartAtConstraint(queryConstraint);
                        break;
                    case "endAt":
                    case "endBefore":
                        queryNonFilterConstraint[i] = new QueryEndAtConstraint(queryConstraint);
                        break;
                }
            }
            return queryNonFilterConstraint;
        }
    }
}
