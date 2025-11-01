package io.capawesome.capacitorjs.plugins.firebase.app;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

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
}
