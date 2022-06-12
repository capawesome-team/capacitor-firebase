package dev.robingenz.capacitorjs.plugins.firebase.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.NonNull;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(name = "FirebaseMessaging", permissions = @Permission(strings = {}, alias = "receive"))
public class FirebaseMessagingPlugin extends Plugin {

    public static final String TAG = "FirebaseMessaging";
    public static final String TOKEN_RECEIVED_EVENT = "tokenReceived";
    public static final String NOTIFICATION_RECEIVED_EVENT = "notificationReceived";
    public static final String NOTIFICATION_ACTION_PERFORMED_EVENT = "notificationActionPerformed";
    public static final String ERROR_NOTIFICATIONS_INVALID = "The provided notifications are invalid.";
    public static final String ERROR_TOPIC_MISSING = "topic must be provided.";
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

    @PluginMethod
    public void isSupported(PluginCall call) {
        JSObject result = new JSObject();
        result.put("isSupported", true);
        call.resolve(result);
    }

    @PluginMethod
    public void getToken(PluginCall call) {
        implementation.getToken(
            new GetTokenResultCallback() {
                @Override
                public void success(String token) {
                    handleTokenReceived(token);
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
    }

    @PluginMethod
    public void deleteToken(PluginCall call) {
        implementation.deleteToken();
        call.resolve();
    }

    @PluginMethod
    public void getDeliveredNotifications(PluginCall call) {
        JSArray notificationsResult = new JSArray();
        StatusBarNotification[] activeNotifications = implementation.getDeliveredNotifications();
        for (StatusBarNotification activeNotification : activeNotifications) {
            JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(activeNotification);
            notificationsResult.put(notificationResult);
        }

        JSObject result = new JSObject();
        result.put("notifications", notificationsResult);
        call.resolve(result);
    }

    @PluginMethod
    public void removeDeliveredNotifications(PluginCall call) {
        JSArray notifications = call.getArray("notifications");

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
    }

    @PluginMethod
    public void removeAllDeliveredNotifications(PluginCall call) {
        implementation.removeAllDeliveredNotifications();
        call.resolve();
    }

    @PluginMethod
    public void subscribeToTopic(PluginCall call) {
        String topic = call.getString("topic");
        if (topic == null) {
            call.reject(ERROR_TOPIC_MISSING);
            return;
        }
        implementation.subscribeToTopic(topic);
        call.resolve();
    }

    @PluginMethod
    public void unsubscribeFromTopic(PluginCall call) {
        String topic = call.getString("topic");
        if (topic == null) {
            call.reject(ERROR_TOPIC_MISSING);
            return;
        }
        implementation.unsubscribeFromTopic(topic);
        call.resolve();
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
