package dev.robingenz.capacitorjs.plugins.firebase.messaging;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingHelper {

    public static JSObject createNotificationResult(@NonNull RemoteMessage remoteMessage) {
        JSObject notificationResult = new JSObject();
        notificationResult.put("id", remoteMessage.getMessageId());

        JSObject data = new JSObject();
        for (String key : remoteMessage.getData().keySet()) {
            Object value = remoteMessage.getData().get(key);
            data.put(key, value);
        }
        notificationResult.put("data", data);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            notificationResult.put("title", notification.getTitle());
            notificationResult.put("body", notification.getBody());
        }
        return notificationResult;
    }

    public static JSObject createNotificationResult(@NonNull Bundle bundle) {
        JSObject notificationResult = new JSObject();
        JSObject data = new JSObject();
        for (String key : bundle.keySet()) {
            if (key.equals("google.message_id")) {
                notificationResult.put("id", bundle.get(key));
            } else {
                data.put(key, bundle.get(key));
            }
        }
        notificationResult.put("data", data);
        return notificationResult;
    }

    public static JSObject createNotificationResult(@NonNull StatusBarNotification statusBarNotification) {
        JSObject notificationResult = new JSObject();
        notificationResult.put("id", statusBarNotification.getId());

        Notification notification = statusBarNotification.getNotification();
        if (notification != null) {
            notificationResult.put("title", notification.extras.getCharSequence(Notification.EXTRA_TITLE));
            notificationResult.put("body", notification.extras.getCharSequence(Notification.EXTRA_TEXT));

            JSObject extras = new JSObject();
            for (String key : notification.extras.keySet()) {
                extras.put(key, notification.extras.get(key));
            }
            notificationResult.put("data", extras);
        }

        return notificationResult;
    }
}
