package dev.robingenz.capacitorjs.plugins.firebase.messaging;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.List;

public class FirebaseMessaging {

    interface RegistrationListener {
        void onRegistrationListener(@NonNull String token);
    }

    interface RegistrationErrorListener {
        void onRegistrationErrorListener(@NonNull String message);
    }

    @Nullable
    private RegistrationListener registrationListener;

    @Nullable
    private RegistrationErrorListener registrationErrorListener;

    private FirebaseMessagingPlugin plugin;
    private NotificationManager notificationManager;
    private com.google.firebase.messaging.FirebaseMessaging firebaseMessagingInstance;

    public FirebaseMessaging(FirebaseMessagingPlugin plugin) {
        this.plugin = plugin;
        this.notificationManager = (NotificationManager) plugin.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        this.firebaseMessagingInstance = com.google.firebase.messaging.FirebaseMessaging.getInstance();
    }

    public void setRegistrationListener(@Nullable RegistrationListener listener) {
        this.registrationListener = listener;
    }

    @Nullable
    public RegistrationListener getRegistrationListener() {
        return registrationListener;
    }

    public void setRegistrationErrorListener(@Nullable RegistrationErrorListener listener) {
        this.registrationErrorListener = listener;
    }

    @Nullable
    public RegistrationErrorListener getRegistrationErrorListener() {
        return registrationErrorListener;
    }

    public void register() {
        this.firebaseMessagingInstance.setAutoInitEnabled(true);
        this.firebaseMessagingInstance.getToken()
            .addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Exception exception = task.getException();
                        Log.w(FirebaseMessagingPlugin.TAG, "Fetching FCM registration token failed", exception);
                        this.registrationErrorListener.onRegistrationErrorListener(exception.getLocalizedMessage());
                        return;
                    }

                    String token = task.getResult();
                    this.registrationListener.onRegistrationListener(token);
                }
            );
    }

    public void unregister() {
        this.firebaseMessagingInstance.deleteToken();
    }

    public StatusBarNotification[] getDeliveredNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return notificationManager.getActiveNotifications();
        } else {
            return new StatusBarNotification[] {};
        }
    }

    public void removeDeliveredNotifications(List<Integer> ids) {
        for (int id : ids) {
            notificationManager.cancel(id);
        }
    }

    public void removeAllDeliveredNotifications() {
        notificationManager.cancelAll();
    }
}
