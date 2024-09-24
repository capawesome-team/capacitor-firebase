package io.capawesome.capacitorjs.plugins.firebase.app;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CapacitorPlugin(name = "FirebaseApp")
public class FirebaseAppPlugin extends Plugin {

    public static final String TAG = "FirebaseApp";

    @PluginMethod
    public void getName(PluginCall call) {
        try {
            String name = getFirebaseAppInstance().getName();

            JSObject result = new JSObject();
            result.put("name", name);
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getOptions(PluginCall call) {
        try {
            FirebaseOptions options = getFirebaseAppInstance().getOptions();

            JSObject result = new JSObject();
            result.put("apiKey", options.getApiKey());
            result.put("applicationId", options.getApplicationId());
            result.put("databaseUrl", options.getDatabaseUrl());
            result.put("gcmSenderId", options.getGcmSenderId());
            result.put("projectId", options.getProjectId());
            result.put("storageBucket", options.getStorageBucket());
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    private FirebaseApp getFirebaseAppInstance() {
        return FirebaseApp.getInstance();
    }

    @PluginMethod
    public void initializeApp(PluginCall call) {
        String name = call.getString("name");
        JSObject config = call.getObject("config");

        FirebaseOptions.Builder options = new FirebaseOptions.Builder();
        options
            .setApplicationId(Objects.requireNonNull(config.getString("appId")))
            .setApiKey(Objects.requireNonNull(config.getString("apiKey")))
            .setProjectId(Objects.requireNonNull(config.getString("projectId")))
            .setStorageBucket(Objects.requireNonNull(config.getString("storageBucket")))
            .setDatabaseUrl(Objects.requireNonNull(config.getString("databaseURL")))
            .setGcmSenderId(Objects.requireNonNull(config.getString("messagingSenderId")));

        try {
            FirebaseApp app = FirebaseApp.initializeApp(this.getContext(), options.build(), name);
            call.resolve();
        } catch (Exception error) {
            call.reject("Could not initialize app with provided firebase config: " + error.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void getApps(PluginCall call) {
        String name = call.getString("name");
        List<FirebaseApp> apps = FirebaseApp.getApps(this.getContext());

        List<String> appNames = new ArrayList<>();

        for (FirebaseApp app : apps) {
            appNames.add(app.getName());
        }
        call.resolve(new JSObject().put("apps", appNames));
    }
}
