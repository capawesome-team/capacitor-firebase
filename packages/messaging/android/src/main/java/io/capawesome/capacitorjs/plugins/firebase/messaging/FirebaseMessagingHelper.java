package io.capawesome.capacitorjs.plugins.firebase.messaging;

import static io.capawesome.capacitorjs.plugins.firebase.messaging.FirebaseMessagingPlugin.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.getcapacitor.util.WebColor;
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
            notificationResult.put("clickAction", notification.getClickAction());
            notificationResult.put("tag", notification.getTag());

            Uri link = notification.getLink();
            if (link != null) {
                notificationResult.put("link", link.toString());
            }
        }
        return notificationResult;
    }

    public static JSObject createNotificationResult(@NonNull Bundle bundle) {
        JSObject notificationResult = new JSObject();
        JSObject data = new JSObject();
        for (String key : bundle.keySet()) {
            if (key.equals("google.message_id")) {
                notificationResult.put("id", "" + bundle.get(key).toString());
            } else {
                data.put(key, bundle.get(key));
            }
        }
        notificationResult.put("data", data);
        return notificationResult;
    }

    public static JSObject createNotificationResult(@NonNull StatusBarNotification statusBarNotification) {
        JSObject notificationResult = new JSObject();
        notificationResult.put("id", "" + statusBarNotification.getId());
        notificationResult.put("tag", statusBarNotification.getTag());

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public static NotificationChannel createNotificationChannelFromPluginCall(PluginCall call, String packageName) {
        String id = call.getString("id");
        if (id == null) {
            return null;
        }
        String name = call.getString("name");
        if (name == null) {
            return null;
        }
        int importance = call.getInt("importance", NotificationManager.IMPORTANCE_DEFAULT);
        String description = call.getString("description", "");
        int visibility = call.getInt("visibility", NotificationCompat.VISIBILITY_PUBLIC);
        boolean vibrate = call.getBoolean("vibrate", false);
        boolean lights = call.getBoolean("lights", false);
        String lightColor = call.getString("lightColor", null);
        String sound = call.getString("sound", null);

        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.setDescription(description);
        notificationChannel.setLockscreenVisibility(visibility);
        notificationChannel.enableVibration(vibrate);
        notificationChannel.enableLights(lights);
        if (lightColor != null) {
            try {
                notificationChannel.setLightColor(WebColor.parseColor(lightColor));
            } catch (Exception exception) {
                Logger.error(TAG, "setLightColor failed.", exception);
            }
        }
        if (sound != null && !sound.isEmpty()) {
            if (sound.contains(".")) {
                sound = sound.substring(0, sound.lastIndexOf('.'));
            }
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/raw/" + sound);
            notificationChannel.setSound(soundUri, audioAttributes);
        }
        return notificationChannel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static JSObject createChannelResult(@NonNull NotificationChannel notificationChannel) {
        JSObject channelResult = new JSObject();
        channelResult.put("id", notificationChannel.getId());
        channelResult.put("name", notificationChannel.getName());
        channelResult.put("description", notificationChannel.getDescription());
        channelResult.put("importance", notificationChannel.getImportance());
        channelResult.put("visibility", notificationChannel.getLockscreenVisibility());
        channelResult.put("sound", notificationChannel.getSound());
        channelResult.put("vibration", notificationChannel.shouldVibrate());
        channelResult.put("lights", notificationChannel.shouldShowLights());
        channelResult.put("lightColor", String.format("#%06X", (0xFFFFFF & notificationChannel.getLightColor())));
        return channelResult;
    }
}
