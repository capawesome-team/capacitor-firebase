package io.capawesome.capacitorjs.plugins.firebase.performance;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FirebasePerformanceHelper {

  @NonNull
  public static Map<String, String> jsObjectToAttributesMap(JSONObject object) throws JSONException {
    Map<String, String> map = new HashMap<>();
    Iterator<String> keys = object.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      map.put(key, object.get(key).toString());
    }
    return map;
  }

  @NonNull
  public static Map<String, Long> jsObjectToMetricsMap(JSONObject object) throws JSONException {
    Map<String, Long> map = new HashMap<>();
    Iterator<String> keys = object.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      if (object.get(key) instanceof Long) {
        map.put(key, (Long) object.get(key));
      } else if (object.get(key) instanceof Double) {
        map.put(key, (long) Math.floor((double) object.get(key)));
      } else {
        throw new JSONException(FirebasePerformancePlugin.ERROR_INVALID_METRIC_VALUE);
      }
    }
    return map;
  }
}
