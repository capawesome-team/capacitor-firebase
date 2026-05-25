package io.capawesome.capacitorjs.plugins.firebase.firestore;

import static io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestorePlugin.ERROR_CODE_PREFIX;

import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryEndAtConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryLimitConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryOrderByConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryStartAtConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseFirestoreHelper {

    public static HashMap<String, Object> createHashMapFromJSONObject(
        JSONObject object,
        @NonNull com.google.firebase.firestore.FirebaseFirestore firestore
    ) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = createObjectFromJSValue(object.get(key), firestore);
            map.put(key, value);
        }
        return map;
    }

    @Nullable
    public static JSObject createJSObjectFromMap(@Nullable Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        JSObject object = new JSObject();
        for (String key : map.keySet()) {
            object.put(key, createJSValueFromNative(map.get(key)));
        }
        return object;
    }

    public static Object createObjectFromJSValue(Object value, @NonNull com.google.firebase.firestore.FirebaseFirestore firestore)
        throws JSONException {
        if (value.toString().equals("null")) {
            return null;
        } else if (value instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) value;
            Object nativeValue = createNativeValueFromMarker(jsonObject, firestore);
            if (nativeValue != null) {
                return nativeValue;
            }
            return createHashMapFromJSONObject(jsonObject, firestore);
        } else if (value instanceof JSONArray) {
            return createArrayListFromJSONArray((JSONArray) value, firestore);
        } else {
            return value;
        }
    }

    @Nullable
    public static QueryCompositeFilterConstraint createQueryCompositeFilterConstraintFromJSObject(
        @Nullable JSObject compositeFilter,
        @NonNull com.google.firebase.firestore.FirebaseFirestore firestore
    ) throws JSONException {
        if (compositeFilter == null) {
            return null;
        } else {
            return new QueryCompositeFilterConstraint(compositeFilter, firestore);
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

    @Nullable
    private static Object createNativeValueFromMarker(
        @NonNull JSONObject jsonObject,
        @NonNull com.google.firebase.firestore.FirebaseFirestore firestore
    ) throws JSONException {
        if (!jsonObject.has("__type__")) {
            return null;
        }
        String type = jsonObject.getString("__type__");
        switch (type) {
            case "timestamp":
                return new Timestamp(jsonObject.getLong("seconds"), jsonObject.getInt("nanoseconds"));
            case "geopoint":
                return new GeoPoint(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
            case "documentReference":
                return firestore.document(jsonObject.getString("path"));
            case "bytes":
                return Blob.fromBytes(Base64.decode(jsonObject.getString("bytes"), Base64.NO_WRAP));
            case "number":
                return parseSpecialNumber(jsonObject.getString("value"));
            case "serverTimestamp":
                return FieldValue.serverTimestamp();
            case "arrayUnion": {
                JSONArray elements = jsonObject.getJSONArray("elements");
                Object[] args = new Object[elements.length()];
                for (int i = 0; i < elements.length(); i++) {
                    args[i] = createObjectFromJSValue(elements.get(i), firestore);
                }
                return FieldValue.arrayUnion(args);
            }
            case "arrayRemove": {
                JSONArray elements = jsonObject.getJSONArray("elements");
                Object[] args = new Object[elements.length()];
                for (int i = 0; i < elements.length(); i++) {
                    args[i] = createObjectFromJSValue(elements.get(i), firestore);
                }
                return FieldValue.arrayRemove(args);
            }
            case "delete":
                return FieldValue.delete();
            case "increment":
                return FieldValue.increment(jsonObject.getDouble("operand"));
            default:
                return null;
        }
    }

    @NonNull
    private static JSObject createJSObjectFromTimestamp(@NonNull Timestamp timestamp) {
        JSObject object = new JSObject();
        object.put("__type__", "timestamp");
        object.put("seconds", timestamp.getSeconds());
        object.put("nanoseconds", timestamp.getNanoseconds());
        return object;
    }

    @NonNull
    private static JSObject createJSObjectFromGeoPoint(@NonNull GeoPoint geoPoint) {
        JSObject object = new JSObject();
        object.put("__type__", "geopoint");
        object.put("latitude", geoPoint.getLatitude());
        object.put("longitude", geoPoint.getLongitude());
        return object;
    }

    @NonNull
    private static JSObject createJSObjectFromDocumentReference(@NonNull DocumentReference reference) {
        JSObject object = new JSObject();
        object.put("__type__", "documentReference");
        object.put("id", reference.getId());
        object.put("path", reference.getPath());
        return object;
    }

    @NonNull
    private static JSObject createJSObjectFromBlob(@NonNull Blob blob) {
        JSObject object = new JSObject();
        object.put("__type__", "bytes");
        object.put("bytes", Base64.encodeToString(blob.toBytes(), Base64.NO_WRAP));
        return object;
    }

    @NonNull
    private static JSObject createJSObjectFromSpecialNumber(double value) {
        JSObject object = new JSObject();
        object.put("__type__", "number");
        if (Double.isNaN(value)) {
            object.put("value", "NaN");
        } else if (value > 0) {
            object.put("value", "Infinity");
        } else {
            object.put("value", "-Infinity");
        }
        return object;
    }

    private static double parseSpecialNumber(String value) {
        switch (value) {
            case "NaN":
                return Double.NaN;
            case "Infinity":
                return Double.POSITIVE_INFINITY;
            case "-Infinity":
                return Double.NEGATIVE_INFINITY;
            default:
                return Double.parseDouble(value);
        }
    }

    private static ArrayList<Object> createArrayListFromJSONArray(
        JSONArray array,
        @NonNull com.google.firebase.firestore.FirebaseFirestore firestore
    ) throws JSONException {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            Object value = array.get(x);
            if (value instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) value;
                Object nativeValue = createNativeValueFromMarker(jsonObject, firestore);
                if (nativeValue != null) {
                    value = nativeValue;
                } else {
                    value = createHashMapFromJSONObject(jsonObject, firestore);
                }
            } else if (value instanceof JSONArray) {
                value = createArrayListFromJSONArray((JSONArray) value, firestore);
            }
            arrayList.add(value);
        }
        return arrayList;
    }

    @Nullable
    private static Object createJSValueFromNative(@Nullable Object value) {
        if (value instanceof Timestamp) {
            return createJSObjectFromTimestamp((Timestamp) value);
        } else if (value instanceof GeoPoint) {
            return createJSObjectFromGeoPoint((GeoPoint) value);
        } else if (value instanceof DocumentReference) {
            return createJSObjectFromDocumentReference((DocumentReference) value);
        } else if (value instanceof Blob) {
            return createJSObjectFromBlob((Blob) value);
        } else if (value instanceof Double && !Double.isFinite((Double) value)) {
            return createJSObjectFromSpecialNumber((Double) value);
        } else if (value instanceof Float && !Float.isFinite((Float) value)) {
            return createJSObjectFromSpecialNumber(((Float) value).doubleValue());
        } else if (value instanceof ArrayList) {
            return createJSArrayFromArrayList((ArrayList) value);
        } else if (value instanceof Map) {
            return createJSObjectFromMap((Map<String, Object>) value);
        }
        return value;
    }

    private static JSArray createJSArrayFromArrayList(ArrayList arrayList) {
        JSArray array = new JSArray();
        for (Object value : arrayList) {
            array.put(createJSValueFromNative(value));
        }
        return array;
    }

    public static JSObject createSnapshotMetadataResult(DocumentSnapshot snapshot) {
        final JSObject obj = new JSObject();
        obj.put("fromCache", snapshot.getMetadata().isFromCache());
        obj.put("hasPendingWrites", snapshot.getMetadata().hasPendingWrites());
        return obj;
    }

    @Nullable
    public static String createErrorCode(@Nullable Exception exception) {
        if (exception == null) {
            return null;
        } else if (exception instanceof FirebaseFirestoreException) {
            String errorCode = ((FirebaseFirestoreException) exception).getCode().name();
            String prefixedErrorCode = String.format("%s/%s", ERROR_CODE_PREFIX, errorCode);
            return snakeToKebabCase(prefixedErrorCode);
        }
        return null;
    }

    private static String snakeToKebabCase(String snakeCase) {
        return snakeCase.replaceAll("_+", "-").toLowerCase();
    }
}
