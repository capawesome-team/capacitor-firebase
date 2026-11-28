package io.capawesome.capacitorjs.plugins.firebase.messaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.CreateChannelOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.DeleteChannelOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.RemoveDeliveredNotificationsOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.SubscribeToTopicOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.UnsubscribeFromTopicOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.GetDeliveredNotificationsResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.GetTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.IsSupportedResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.ListChannelsResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.NonEmptyResultCallback;
import java.util.List;

public class FirebaseMessaging {

    private NotificationManager notificationManager;
    private FirebaseMessagingPlugin plugin;

    public FirebaseMessaging(FirebaseMessagingPlugin plugin) {
        this.plugin = plugin;
        this.notificationManager = (NotificationManager) plugin.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(@NonNull CreateChannelOptions options, @NonNull EmptyResultCallback callback) {
        NotificationChannel notificationChannel = FirebaseMessagingHelper.createNotificationChannel(
            options,
            plugin.getContext().getPackageName()
        );
        notificationManager.createNotificationChannel(notificationChannel);
        callback.success();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteChannel(@NonNull DeleteChannelOptions options, @NonNull EmptyResultCallback callback) {
        notificationManager.deleteNotificationChannel(options.getId());
        callback.success();
    }

    public void deleteToken(@NonNull EmptyResultCallback callback) {
        getFirebaseMessagingInstance()
            .deleteToken()
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void getDeliveredNotifications(@NonNull NonEmptyResultCallback<GetDeliveredNotificationsResult> callback) {
        StatusBarNotification[] notifications;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notifications = notificationManager.getActiveNotifications();
        } else {
            notifications = new StatusBarNotification[] {};
        }
        GetDeliveredNotificationsResult result = new GetDeliveredNotificationsResult(notifications);
        callback.success(result);
    }

    public void getToken(@NonNull NonEmptyResultCallback<GetTokenResult> callback) {
        getFirebaseMessagingInstance().setAutoInitEnabled(true);
        getFirebaseMessagingInstance()
            .getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    callback.error(task.getException());
                    return;
                }

                String token = task.getResult();
                GetTokenResult result = new GetTokenResult(token);
                callback.success(result);
            });
    }

    public void isSupported(@NonNull NonEmptyResultCallback<IsSupportedResult> callback) {
        IsSupportedResult result = new IsSupportedResult(true);
        callback.success(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void listChannels(@NonNull NonEmptyResultCallback<ListChannelsResult> callback) {
        List<NotificationChannel> channels = notificationManager.getNotificationChannels();
        ListChannelsResult result = new ListChannelsResult(channels);
        callback.success(result);
    }

    public void removeAllDeliveredNotifications(@NonNull EmptyResultCallback callback) {
        notificationManager.cancelAll();
        callback.success();
    }

    public void removeDeliveredNotifications(@NonNull RemoveDeliveredNotificationsOptions options, @NonNull EmptyResultCallback callback) {
        List<String> tags = options.getTags();
        List<String> ids = options.getIds();
        for (int i = 0; i < tags.size(); i++) {
            try {
                String tag = tags.get(i);
                int id = Integer.parseInt(ids.get(i));
                notificationManager.cancel(tag, id);
            } catch (NumberFormatException exception) {
                Log.w(FirebaseMessagingPlugin.TAG, "removeDeliveredNotifications failed", exception);
            }
        }
        callback.success();
    }

    public void subscribeToTopic(@NonNull SubscribeToTopicOptions options, @NonNull EmptyResultCallback callback) {
        if (!isGooglePlayServicesInstalled()) {
            callback.error(CustomExceptions.GOOGLE_PLAY_SERVICES_UNAVAILABLE);
            return;
        }
        getFirebaseMessagingInstance()
            .subscribeToTopic(options.getTopic())
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void unsubscribeFromTopic(@NonNull UnsubscribeFromTopicOptions options, @NonNull EmptyResultCallback callback) {
        if (!isGooglePlayServicesInstalled()) {
            callback.error(CustomExceptions.GOOGLE_PLAY_SERVICES_UNAVAILABLE);
            return;
        }
        getFirebaseMessagingInstance()
            .unsubscribeFromTopic(options.getTopic())
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    private com.google.firebase.messaging.FirebaseMessaging getFirebaseMessagingInstance() {
        return com.google.firebase.messaging.FirebaseMessaging.getInstance();
    }

    private boolean isGooglePlayServicesInstalled() {
        // The Firebase SDK makes the same check and never completes a topic task when it fails.
        int permissionState = plugin
            .getContext()
            .getPackageManager()
            .checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms");
        return permissionState != PackageManager.PERMISSION_DENIED;
    }
}
