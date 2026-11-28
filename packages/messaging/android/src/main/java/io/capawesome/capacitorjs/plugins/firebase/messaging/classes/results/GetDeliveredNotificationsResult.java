package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results;

import android.service.notification.StatusBarNotification;
import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.FirebaseMessagingHelper;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class GetDeliveredNotificationsResult implements Result {

    @NonNull
    private final StatusBarNotification[] notifications;

    public GetDeliveredNotificationsResult(@NonNull StatusBarNotification[] notifications) {
        this.notifications = notifications;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray notificationsResult = new JSArray();
        for (StatusBarNotification notification : notifications) {
            JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(notification);
            notificationsResult.put(notificationResult);
        }

        JSObject result = new JSObject();
        result.put("notifications", notificationsResult);
        return result;
    }
}
