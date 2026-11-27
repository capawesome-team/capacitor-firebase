package io.capawesome.capacitorjs.plugins.firebase.messaging;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onRegistered(@NonNull String installationId) {
        super.onRegistered(installationId);
        FirebaseMessagingPlugin.onRegistered(installationId);
    }

    @Override
    public void onUnregistered(@NonNull String installationId) {
        super.onUnregistered(installationId);
        FirebaseMessagingPlugin.onUnregistered(installationId);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseMessagingPlugin.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseMessagingPlugin.onMessageReceived(remoteMessage);
    }
}
