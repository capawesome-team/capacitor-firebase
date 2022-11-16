package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import android.util.Log;

public class FirebaseRemoteConfig {

    private final com.google.firebase.remoteconfig.FirebaseRemoteConfig remoteConfigInstance;

    public FirebaseRemoteConfig() {
        this.remoteConfigInstance = com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance();
    }

    public void activate(final ActivateResultCallback resultCallback) {
        remoteConfigInstance
            .activate()
            .addOnSuccessListener(
                result -> {
                    resultCallback.success(result.booleanValue());
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(FirebaseRemoteConfigPlugin.TAG, "Activate config failed.", exception);
                    resultCallback.error(exception.getLocalizedMessage());
                }
            );
    }

    public void fetchAndActivate(final ActivateResultCallback resultCallback) {
        remoteConfigInstance
            .fetchAndActivate()
            .addOnSuccessListener(
                result -> {
                    resultCallback.success(result.booleanValue());
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(FirebaseRemoteConfigPlugin.TAG, "Fetch and activate config failed.", exception);
                    resultCallback.error(exception.getLocalizedMessage());
                }
            );
    }

    public void fetchConfig(long minimumFetchIntervalInSeconds, final FetchConfigResultCallback resultCallback) {
        remoteConfigInstance
            .fetch(minimumFetchIntervalInSeconds)
            .addOnSuccessListener(
                result -> {
                    resultCallback.success();
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(FirebaseRemoteConfigPlugin.TAG, "Fetch config failed.", exception);
                    resultCallback.error(exception.getLocalizedMessage());
                }
            );
    }

    public boolean getBoolean(String key) {
        return remoteConfigInstance.getBoolean(key);
    }

    public double getNumber(String key) {
        return remoteConfigInstance.getDouble(key);
    }

    public String getString(String key) {
        return remoteConfigInstance.getString(key);
    }
}
