package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomExceptions;

public class DeleteChannelOptions {

    @NonNull
    private final String id;

    public DeleteChannelOptions(@NonNull PluginCall call) throws Exception {
        this.id = DeleteChannelOptions.getIdFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    private static String getIdFromCall(@NonNull PluginCall call) throws Exception {
        String id = call.getString("id");
        if (id == null) {
            throw CustomExceptions.ID_MISSING;
        }
        return id;
    }
}
