package io.capawesome.capacitorjs.plugins.firebase.messaging;

import android.Manifest;
import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import androidx.annotation.NonNull;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(
    name = "FirebaseMessaging",
    permissions = @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = FirebaseMessagingPlugin.PUSH_NOTIFICATIONS)
)
public class FirebaseMessagingPlugin extends Plugin {

    public static final String PUSH_NOTIFICATIONS = "receive";

    public static final String TAG = "FirebaseMessaging";
    public static final String TOKEN_RECEIVED_EVENT = "tokenReceived";
    public static final String NOTIFICATION_RECEIVED_EVENT = "notificationReceived";
    public static final String NOTIFICATION_ACTION_PERFORMED_EVENT = "notificationActionPerformed";
    public static final String ERROR_NOTIFICATIONS_INVALID = "The provided notifications are invalid.";
    public static final String ERROR_TOPIC_MISSING = "topic must be provided.";
    public static final String ERROR_NOTIFICATIONS_MISSING = "notifications must be provided.";
    public static final String ERROR_ID_MISSING = "id must be provided.";
    public static final String ERROR_ID_OR_NAME_MISSING = "id and name must be provided.";
    public static Bridge staticBridge = null;
    public static String lastToken = null;
    public static RemoteMessage lastRemoteMessage = null;
    private FirebaseMessaging implementation;

    public void load() {
        implementation = new FirebaseMessaging(this);
        staticBridge = this.bridge;

        if (lastToken != null) {
            handleTokenReceived(lastToken);
            lastToken = null;
        }
        if (lastRemoteMessage != null) {
            handleNotificationReceived(lastRemoteMessage);
            lastRemoteMessage = null;
        }
    }

    @Override
    protected void handleOnNewIntent(Intent data) {
        super.handleOnNewIntent(data);
        Bundle bundle = data.getExtras();
        if (bundle != null && bundle.containsKey("google.message_id")) {
            this.handleNotificationActionPerformed(bundle);
        }
    }

    public static void onNewToken(@NonNull String token) {
        FirebaseMessagingPlugin plugin = FirebaseMessagingPlugin.getFirebaseMessagingPluginInstance();
        if (plugin != null) {
            plugin.handleTokenReceived(token);
        } else {
            lastToken = token;
        }
    }

    public static void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        FirebaseMessagingPlugin plugin = FirebaseMessagingPlugin.getFirebaseMessagingPluginInstance();
        if (plugin != null) {
            plugin.handleNotificationReceived(remoteMessage);
        } else {
            lastRemoteMessage = remoteMessage;
        }
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            JSObject result = new JSObject();
            result.put("receive", "granted");
            call.resolve(result);
        } else {
            super.checkPermissions(call);
        }
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            JSObject result = new JSObject();
            result.put("receive", "granted");
            call.resolve(result);
        } else {
            if (getPermissionState(PUSH_NOTIFICATIONS) == PermissionState.GRANTED) {
                this.checkPermissions(call);
            } else {
                requestPermissionForAlias(PUSH_NOTIFICATIONS, call, "permissionsCallback");
            }
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        JSObject result = new JSObject();
        result.put("isSupported", true);
        call.resolve(result);
    }

    @PluginMethod
    public void getToken(PluginCall call) {
        try {
            implementation.getToken(
                new GetTokenResultCallback() {
                    @Override
                    public void success(String token) {
                        JSObject result = new JSObject();
                        result.put("token", token);
                        call.resolve(result);
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void deleteToken(PluginCall call) {
        try {
            implementation.deleteToken();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getDeliveredNotifications(PluginCall call) {
        try {
            JSArray notificationsResult = new JSArray();
            StatusBarNotification[] activeNotifications = implementation.getDeliveredNotifications();
            for (StatusBarNotification activeNotification : activeNotifications) {
                JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(activeNotification);
                notificationsResult.put(notificationResult);
            }

            JSObject result = new JSObject();
            result.put("notifications", notificationsResult);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void removeDeliveredNotifications(PluginCall call) {
        try {
            JSArray notifications = call.getArray("notifications");
            if (notifications == null) {
                call.reject(ERROR_NOTIFICATIONS_MISSING);
                return;
            }

            List<String> tags = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            try {
                for (Object item : notifications.toList()) {
                    if (item instanceof JSONObject) {
                        JSObject notification = JSObject.fromJSONObject((JSONObject) item);
                        String tag = notification.getString("tag", "");
                        tags.add(tag);
                        String id = notification.getString("id", "");
                        ids.add(id);
                    } else {
                        call.reject(ERROR_NOTIFICATIONS_INVALID);
                        return;
                    }
                }
            } catch (JSONException e) {
                call.reject(ERROR_NOTIFICATIONS_INVALID);
                return;
            }

            implementation.removeDeliveredNotifications(tags, ids);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void removeAllDeliveredNotifications(PluginCall call) {
        try {
            implementation.removeAllDeliveredNotifications();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void subscribeToTopic(PluginCall call) {
        try {
            String topic = call.getString("topic");
            if (topic == null) {
                call.reject(ERROR_TOPIC_MISSING);
                return;
            }
            implementation.subscribeToTopic(topic);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void unsubscribeFromTopic(PluginCall call) {
        try {
            String topic = call.getString("topic");
            if (topic == null) {
                call.reject(ERROR_TOPIC_MISSING);
                return;
            }
            implementation.unsubscribeFromTopic(topic);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void createChannel(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                call.unavailable();
                return;
            }
            NotificationChannel notificationChannel = FirebaseMessagingHelper.createNotificationChannelFromPluginCall(
                call,
                getContext().getPackageName()
            );
            if (notificationChannel == null) {
                call.reject(ERROR_ID_OR_NAME_MISSING);
                return;
            }
            implementation.createChannel(notificationChannel);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void deleteChannel(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                call.unavailable();
                return;
            }
            String id = call.getString("id");
            if (id == null) {
                call.reject(ERROR_ID_MISSING);
                return;
            }

            implementation.deleteChannelById(id);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void listChannels(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                call.unavailable();
                return;
            }
            List<NotificationChannel> notificationChannels = implementation.getNotificationChannels();
            JSArray channelsResult = new JSArray();
            for (NotificationChannel notificationChannel : notificationChannels) {
                JSObject channelResult = FirebaseMessagingHelper.createChannelResult(notificationChannel);
                channelsResult.put(channelResult);
            }
            JSObject result = new JSObject();
            result.put("channels", channelsResult);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    private void handleTokenReceived(@NonNull String token) {
        JSObject result = new JSObject();
        result.put("token", token);
        notifyListeners(TOKEN_RECEIVED_EVENT, result, true);
    }

    private void handleNotificationReceived(@NonNull RemoteMessage remoteMessage) {
        JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(remoteMessage);
        JSObject result = new JSObject();
        result.put("notification", notificationResult);
        notifyListeners(NOTIFICATION_RECEIVED_EVENT, result, true);
    }

    private void handleNotificationActionPerformed(@NonNull Bundle bundle) {
        JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(bundle);
        JSObject result = new JSObject();
        result.put("actionId", "tap");
        result.put("notification", notificationResult);
        notifyListeners(NOTIFICATION_ACTION_PERFORMED_EVENT, result, true);
    }

    private static FirebaseMessagingPlugin getFirebaseMessagingPluginInstance() {
        if (staticBridge == null || staticBridge.getWebView() == null) {
            return null;
        }
        PluginHandle handle = staticBridge.getPlugin("FirebaseMessaging");
        if (handle == null) {
            return null;
        }
        return (FirebaseMessagingPlugin) handle.getInstance();
    }
}
