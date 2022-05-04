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
import java.util.List;
import org.json.JSONException;

@CapacitorPlugin(name = "FirebaseMessaging", permissions = @Permission(strings = {}, alias = "receive"))
public class FirebaseMessagingPlugin extends Plugin {

    public static final String TAG = "FirebaseMessaging";
    public static final String NOTIFICATION_RECEIVED_EVENT = "notificationReceived";
    public static final String NOTIFICATION_ACTION_PERFORMED_EVENT = "notificationActionPerformed";
    public static final String ERROR_IDS_MISSING = "ids must be provided.";
    public static Bridge staticBridge = null;
    public static RemoteMessage lastRemoteMessage = null;
    private FirebaseMessaging implementation;

    public void load() {
        implementation = new FirebaseMessaging(this);

        staticBridge = this.bridge;
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

    public static void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        FirebaseMessagingPlugin plugin = FirebaseMessagingPlugin.getFirebaseMessagingPluginInstance();
        if (plugin != null) {
            plugin.handleNotificationReceived(remoteMessage);
        } else {
            lastRemoteMessage = remoteMessage;
        }
    }

    @PluginMethod
    public void getToken(PluginCall call) {
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
        List<Integer> ids = null;
        try {
            ids = call.getArray("ids").toList();
        } catch (JSONException e) {
            Log.w(TAG, e.getLocalizedMessage());
        }
        if (ids == null || ids.size() == 0) {
            call.reject(ERROR_IDS_MISSING);
            return;
        }

        implementation.removeDeliveredNotifications(ids);
        call.resolve();
    }

    @PluginMethod
    public void removeAllDeliveredNotifications(PluginCall call) {
        implementation.removeAllDeliveredNotifications();
        call.resolve();
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
