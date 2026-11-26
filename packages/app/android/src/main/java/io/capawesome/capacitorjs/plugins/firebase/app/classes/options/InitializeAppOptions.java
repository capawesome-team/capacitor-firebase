package io.capawesome.capacitorjs.plugins.firebase.app.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.CustomExceptions;

public class InitializeAppOptions {

    @NonNull
    private final String name;

    @NonNull
    private final com.google.firebase.FirebaseOptions options;

    public InitializeAppOptions(@NonNull PluginCall call) throws Exception {
        this.name = InitializeAppOptions.getNameFromCall(call);
        this.options = InitializeAppOptions.getOptionsFromCall(call);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public com.google.firebase.FirebaseOptions getOptions() {
        return options;
    }

    @NonNull
    private static String getNameFromCall(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        if (!name.matches("^[A-Za-z0-9_-]+$")) {
            throw CustomExceptions.NAME_INVALID;
        }
        return name;
    }

    @NonNull
    private static com.google.firebase.FirebaseOptions getOptionsFromCall(@NonNull PluginCall call) throws Exception {
        JSObject options = call.getObject("options");
        if (options == null) {
            throw CustomExceptions.OPTIONS_MISSING;
        }
        String apiKey = options.getString("apiKey");
        if (apiKey == null) {
            throw CustomExceptions.API_KEY_MISSING;
        }
        String applicationId = options.getString("applicationId");
        if (applicationId == null) {
            throw CustomExceptions.APPLICATION_ID_MISSING;
        }
        String gcmSenderId = options.getString("gcmSenderId");
        if (gcmSenderId == null) {
            throw CustomExceptions.GCM_SENDER_ID_MISSING;
        }
        String projectId = options.getString("projectId");
        if (projectId == null) {
            throw CustomExceptions.PROJECT_ID_MISSING;
        }
        return new com.google.firebase.FirebaseOptions.Builder()
            .setApiKey(apiKey)
            .setApplicationId(applicationId)
            .setDatabaseUrl(options.getString("databaseUrl"))
            .setGcmSenderId(gcmSenderId)
            .setProjectId(projectId)
            .setStorageBucket(options.getString("storageBucket"))
            .build();
    }
}
