package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomExceptions;

public class SubscribeToTopicOptions {

    @NonNull
    private final String topic;

    public SubscribeToTopicOptions(@NonNull PluginCall call) throws Exception {
        this.topic = SubscribeToTopicOptions.getTopicFromCall(call);
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    private static String getTopicFromCall(@NonNull PluginCall call) throws Exception {
        String topic = call.getString("topic");
        if (topic == null) {
            throw CustomExceptions.TOPIC_MISSING;
        }
        return topic;
    }
}
