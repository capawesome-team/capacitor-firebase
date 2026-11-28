package io.capawesome.capacitorjs.plugins.firebase.messaging;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Bridge;
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
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events.NotificationActionPerformedEvent;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events.NotificationReceivedEvent;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events.TokenReceivedEvent;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.CreateChannelOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.DeleteChannelOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.RemoveDeliveredNotificationsOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.SubscribeToTopicOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options.UnsubscribeFromTopicOptions;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.GetDeliveredNotificationsResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.GetTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.IsSupportedResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.ListChannelsResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results.PermissionStatusResult;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;
import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(
    name = "FirebaseMessaging",
    permissions = @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = FirebaseMessagingPlugin.PUSH_NOTIFICATIONS)
)
public class FirebaseMessagingPlugin extends Plugin {

    public static final String EVENT_NOTIFICATION_ACTION_PERFORMED = "notificationActionPerformed";
    public static final String EVENT_NOTIFICATION_RECEIVED = "notificationReceived";
    public static final String EVENT_TOKEN_RECEIVED = "tokenReceived";
    public static final String PUSH_NOTIFICATIONS = "receive";
    public static final String TAG = "FirebaseMessaging";
    public static String lastToken = null;
    public static Bridge staticBridge = null;
    private FirebaseMessaging implementation;
    private static final List<RemoteMessage> pendingRemoteMessages = new ArrayList<>();

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            resolveCall(call, new PermissionStatusResult("granted"));
            return;
        }
        super.checkPermissions(call);
    }

    @PluginMethod
    public void createChannel(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                rejectCallAsUnavailable(call);
                return;
            }
            CreateChannelOptions options = new CreateChannelOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.createChannel(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void deleteChannel(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                rejectCallAsUnavailable(call);
                return;
            }
            DeleteChannelOptions options = new DeleteChannelOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.deleteChannel(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void deleteToken(PluginCall call) {
        try {
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.deleteToken(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDeliveredNotifications(PluginCall call) {
        try {
            NonEmptyResultCallback<GetDeliveredNotificationsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDeliveredNotificationsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getDeliveredNotifications(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getToken(PluginCall call) {
        try {
            NonEmptyResultCallback<GetTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getToken(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        try {
            NonEmptyResultCallback<IsSupportedResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsSupportedResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isSupported(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void listChannels(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                rejectCallAsUnavailable(call);
                return;
            }
            NonEmptyResultCallback<ListChannelsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ListChannelsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.listChannels(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void load() {
        implementation = new FirebaseMessaging(this);
        synchronized (pendingRemoteMessages) {
            staticBridge = this.bridge;
            for (RemoteMessage remoteMessage : pendingRemoteMessages) {
                notifyNotificationReceivedListeners(new NotificationReceivedEvent(remoteMessage));
            }
            pendingRemoteMessages.clear();
        }

        if (lastToken != null) {
            notifyTokenReceivedListeners(new TokenReceivedEvent(lastToken));
            lastToken = null;
        }
    }

    public void notifyNotificationActionPerformedListeners(@NonNull NotificationActionPerformedEvent event) {
        notifyListeners(EVENT_NOTIFICATION_ACTION_PERFORMED, event.toJSObject(), true);
    }

    public void notifyNotificationReceivedListeners(@NonNull NotificationReceivedEvent event) {
        notifyListeners(EVENT_NOTIFICATION_RECEIVED, event.toJSObject(), true);
    }

    public void notifyTokenReceivedListeners(@NonNull TokenReceivedEvent event) {
        notifyListeners(EVENT_TOKEN_RECEIVED, event.toJSObject(), true);
    }

    public static void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        synchronized (pendingRemoteMessages) {
            FirebaseMessagingPlugin plugin = FirebaseMessagingPlugin.getFirebaseMessagingPluginInstance();
            if (plugin != null) {
                plugin.notifyNotificationReceivedListeners(new NotificationReceivedEvent(remoteMessage));
            } else {
                pendingRemoteMessages.add(remoteMessage);
            }
        }
    }

    public static void onNewToken(@NonNull String token) {
        FirebaseMessagingPlugin plugin = FirebaseMessagingPlugin.getFirebaseMessagingPluginInstance();
        if (plugin != null) {
            plugin.notifyTokenReceivedListeners(new TokenReceivedEvent(token));
        } else {
            lastToken = token;
        }
    }

    @PluginMethod
    public void removeAllDeliveredNotifications(PluginCall call) {
        try {
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.removeAllDeliveredNotifications(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeDeliveredNotifications(PluginCall call) {
        try {
            RemoveDeliveredNotificationsOptions options = new RemoveDeliveredNotificationsOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.removeDeliveredNotifications(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            resolveCall(call, new PermissionStatusResult("granted"));
            return;
        }
        if (getPermissionState(PUSH_NOTIFICATIONS) == PermissionState.GRANTED) {
            this.checkPermissions(call);
            return;
        }
        requestPermissionForAlias(PUSH_NOTIFICATIONS, call, "permissionsCallback");
    }

    @PluginMethod
    public void subscribeToTopic(PluginCall call) {
        try {
            SubscribeToTopicOptions options = new SubscribeToTopicOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.subscribeToTopic(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unsubscribeFromTopic(PluginCall call) {
        try {
            UnsubscribeFromTopicOptions options = new UnsubscribeFromTopicOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.unsubscribeFromTopic(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnNewIntent(Intent data) {
        super.handleOnNewIntent(data);
        Bundle bundle = data.getExtras();
        if (bundle != null && bundle.containsKey("google.message_id")) {
            notifyNotificationActionPerformedListeners(new NotificationActionPerformedEvent(bundle));
        }
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

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException ? ((CustomException) exception).getCode() : null;
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable();
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
