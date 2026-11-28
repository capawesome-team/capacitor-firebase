package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.firebase.messaging.RemoteMessage;
import io.capawesome.capacitorjs.plugins.firebase.messaging.FirebaseMessagingHelper;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class NotificationReceivedEvent implements Result {

    @NonNull
    private final RemoteMessage remoteMessage;

    public NotificationReceivedEvent(@NonNull RemoteMessage remoteMessage) {
        this.remoteMessage = remoteMessage;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(remoteMessage);
        JSObject result = new JSObject();
        result.put("notification", notificationResult);
        return result;
    }
}
