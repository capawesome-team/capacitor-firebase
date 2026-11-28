package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveDeliveredNotificationsOptions {

    @NonNull
    private final List<String> ids;

    @NonNull
    private final List<String> tags;

    public RemoveDeliveredNotificationsOptions(@NonNull PluginCall call) throws Exception {
        List<JSObject> notifications = RemoveDeliveredNotificationsOptions.getNotificationsFromCall(call);
        this.tags = RemoveDeliveredNotificationsOptions.getTagsFromNotifications(notifications);
        this.ids = RemoveDeliveredNotificationsOptions.getIdsFromNotifications(notifications);
    }

    @NonNull
    public List<String> getIds() {
        return ids;
    }

    @NonNull
    public List<String> getTags() {
        return tags;
    }

    @NonNull
    private static List<String> getIdsFromNotifications(@NonNull List<JSObject> notifications) {
        List<String> ids = new ArrayList<>();
        for (JSObject notification : notifications) {
            ids.add(notification.getString("id", ""));
        }
        return ids;
    }

    @NonNull
    private static List<JSObject> getNotificationsFromCall(@NonNull PluginCall call) throws Exception {
        JSArray items = call.getArray("notifications");
        if (items == null) {
            throw CustomExceptions.NOTIFICATIONS_MISSING;
        }
        List<JSObject> notifications = new ArrayList<>();
        try {
            for (Object item : items.toList()) {
                if (!(item instanceof JSONObject)) {
                    throw CustomExceptions.NOTIFICATIONS_INVALID;
                }
                notifications.add(JSObject.fromJSONObject((JSONObject) item));
            }
        } catch (JSONException exception) {
            throw CustomExceptions.NOTIFICATIONS_INVALID;
        }
        return notifications;
    }

    @NonNull
    private static List<String> getTagsFromNotifications(@NonNull List<JSObject> notifications) {
        List<String> tags = new ArrayList<>();
        for (JSObject notification : notifications) {
            tags.add(notification.getString("tag", ""));
        }
        return tags;
    }
}
