package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomExceptions;

public class SetUserIdOptions {

    @NonNull
    private final String userId;

    public SetUserIdOptions(@NonNull PluginCall call) throws Exception {
        this.userId = SetUserIdOptions.getUserIdFromCall(call);
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    private static String getUserIdFromCall(@NonNull PluginCall call) throws Exception {
        String userId = call.getString("userId");
        if (userId == null) {
            throw CustomExceptions.USER_ID_MISSING;
        }
        return userId;
    }
}
