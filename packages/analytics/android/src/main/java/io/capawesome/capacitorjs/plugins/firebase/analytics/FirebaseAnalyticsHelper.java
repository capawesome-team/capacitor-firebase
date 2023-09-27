package io.capawesome.capacitorjs.plugins.firebase.analytics;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseAnalyticsHelper {

    public static Bundle createBundleFromJson(@Nullable JSONObject json) {
        Bundle bundle = new Bundle();
        try {
            Iterator<String> iterator = json.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Object value = json.get(key);
                switch (value.getClass().getSimpleName()) {
                    case "Boolean":
                        bundle.putBoolean(key, (Boolean) value);
                        break;
                    case "Integer":
                        bundle.putInt(key, (Integer) value);
                        break;
                    case "Double":
                        bundle.putDouble(key, (Double) value);
                        break;
                    case "Long":
                        bundle.putLong(key, (Long) value);
                        break;
                    case "Float":
                        bundle.putFloat(key, (Float) value);
                        break;
                    case "JSONObject":
                        bundle.putBundle(key, createBundleFromJson((JSONObject) value));
                        break;
                    case "JSONArray":
                        JSONArray array = (JSONArray) value;
                        Object firstItem = array.length() == 0 ? null : array.get(0);
                        if (firstItem == null) {
                            break;
                        }
                        switch (firstItem.getClass().getSimpleName()) {
                            case "Boolean":
                                {
                                    boolean[] items = new boolean[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = (boolean) array.get(index);
                                    }
                                    bundle.putBooleanArray(key, items);
                                    break;
                                }
                            case "Integer":
                                {
                                    int[] items = new int[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = (int) array.get(index);
                                    }
                                    bundle.putIntArray(key, items);
                                    break;
                                }
                            case "Double":
                                {
                                    double[] items = new double[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = (double) array.get(index);
                                    }
                                    bundle.putDoubleArray(key, items);
                                    break;
                                }
                            case "Long":
                                {
                                    long[] items = new long[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = (long) array.get(index);
                                    }
                                    bundle.putLongArray(key, items);
                                    break;
                                }
                            case "Float":
                                {
                                    float[] items = new float[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = (float) array.get(index);
                                    }
                                    bundle.putFloatArray(key, items);
                                    break;
                                }
                            case "JSONObject":
                                {
                                    Bundle[] items = new Bundle[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = createBundleFromJson(array.getJSONObject(index));
                                    }
                                    bundle.putParcelableArray(key, items);
                                    break;
                                }
                            default:
                                {
                                    String[] items = new String[array.length()];
                                    for (int index = 0; index < array.length(); index++) {
                                        items[index] = array.getString(index);
                                    }
                                    bundle.putStringArray(key, items);
                                    break;
                                }
                        }
                        break;
                    default:
                        bundle.putString(key, (String) value);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return bundle;
    }
}
