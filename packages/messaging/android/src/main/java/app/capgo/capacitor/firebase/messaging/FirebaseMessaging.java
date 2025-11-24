package app.capgo.capacitor.firebase.messaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.List;

public class FirebaseMessaging {

    private FirebaseMessagingPlugin plugin;
    private NotificationManager notificationManager;

    public FirebaseMessaging(FirebaseMessagingPlugin plugin) {
        this.plugin = plugin;
        this.notificationManager = (NotificationManager) plugin.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void getToken(final GetTokenResultCallback resultCallback) {
        getFirebaseMessagingInstance().setAutoInitEnabled(true);
        getFirebaseMessagingInstance()
            .getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Exception exception = task.getException();
                    Log.w(FirebaseMessagingPlugin.TAG, "Fetching FCM registration token failed", exception);
                    resultCallback.error(exception.getMessage());
                    return;
                }

                String token = task.getResult();
                resultCallback.success(token);
            });
    }

    public void deleteToken() {
        getFirebaseMessagingInstance().deleteToken();
    }

    public StatusBarNotification[] getDeliveredNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return notificationManager.getActiveNotifications();
        } else {
            return new StatusBarNotification[] {};
        }
    }

    public void removeDeliveredNotifications(List<String> tags, List<String> ids) {
        for (int i = 0; i < tags.size(); i++) {
            try {
                String tag = tags.get(i);
                int id = Integer.parseInt(ids.get(i));
                notificationManager.cancel(tag, id);
            } catch (NumberFormatException exception) {
                Log.w(FirebaseMessagingPlugin.TAG, "removeDeliveredNotifications failed", exception);
            }
        }
    }

    public void removeAllDeliveredNotifications() {
        notificationManager.cancelAll();
    }

    public void subscribeToTopic(String topic) {
        getFirebaseMessagingInstance().subscribeToTopic(topic);
    }

    public void unsubscribeFromTopic(String topic) {
        getFirebaseMessagingInstance().unsubscribeFromTopic(topic);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(NotificationChannel notificationChannel) {
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteChannelById(String id) {
        notificationManager.deleteNotificationChannel(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<NotificationChannel> getNotificationChannels() {
        return notificationManager.getNotificationChannels();
    }

    private com.google.firebase.messaging.FirebaseMessaging getFirebaseMessagingInstance() {
        return com.google.firebase.messaging.FirebaseMessaging.getInstance();
    }
}
