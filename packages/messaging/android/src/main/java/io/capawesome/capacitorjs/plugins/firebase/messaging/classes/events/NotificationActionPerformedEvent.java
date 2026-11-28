package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.events;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.FirebaseMessagingHelper;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;

public class NotificationActionPerformedEvent implements Result {

    @NonNull
    private final Bundle bundle;

    public NotificationActionPerformedEvent(@NonNull Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject notificationResult = FirebaseMessagingHelper.createNotificationResult(bundle);
        JSObject result = new JSObject();
        result.put("actionId", "tap");
        result.put("notification", notificationResult);
        return result;
    }
}
