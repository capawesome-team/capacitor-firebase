package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

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
                    resultCallback.error(exception.getMessage());
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
                    resultCallback.error(exception.getMessage());
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
                    resultCallback.error(exception.getMessage());
                }
            );
    }

    public GetValueResult<Boolean> getBoolean(String key) {
        FirebaseRemoteConfigValue value = remoteConfigInstance.getValue(key);
        return new GetValueResult<Boolean>(value.asBoolean(), value.getSource());
    }

    public GetValueResult<Double> getNumber(String key) {
        FirebaseRemoteConfigValue value = remoteConfigInstance.getValue(key);
        return new GetValueResult<Double>(value.asDouble(), value.getSource());
    }

    public GetValueResult<String> getString(String key) {
        FirebaseRemoteConfigValue value = remoteConfigInstance.getValue(key);
        return new GetValueResult<String>(value.asString(), value.getSource());
    }
}
