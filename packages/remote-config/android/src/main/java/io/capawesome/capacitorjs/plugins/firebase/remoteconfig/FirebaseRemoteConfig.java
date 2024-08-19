package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import static io.capawesome.capacitorjs.plugins.firebase.remoteconfig.FirebaseRemoteConfigPlugin.TAG;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.events.AddConfigUpdateListenerOptionsCallbackEvent;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.AddConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.RemoveConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.NonEmptyResultCallback;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FirebaseRemoteConfig {

    private final FirebaseRemoteConfigPlugin plugin;
    private final Map<String, ConfigUpdateListenerRegistration> listenerRegistrationMap = new HashMap<>();

    public FirebaseRemoteConfig(FirebaseRemoteConfigPlugin plugin) {
        this.plugin = plugin;
    }

    public void activate(final ActivateResultCallback resultCallback) {
        getFirebaseRemoteConfigInstance()
            .activate()
            .addOnSuccessListener(
                result -> {
                    resultCallback.success(result.booleanValue());
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(TAG, "Activate config failed.", exception);
                    resultCallback.error(exception.getMessage());
                }
            );
    }

    public void fetchAndActivate(final ActivateResultCallback resultCallback) {
        getFirebaseRemoteConfigInstance()
            .fetchAndActivate()
            .addOnSuccessListener(
                result -> {
                    resultCallback.success(result.booleanValue());
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(TAG, "Fetch and activate config failed.", exception);
                    resultCallback.error(exception.getMessage());
                }
            );
    }

    public void fetchConfig(long minimumFetchIntervalInSeconds, final FetchConfigResultCallback resultCallback) {
        getFirebaseRemoteConfigInstance()
            .fetch(minimumFetchIntervalInSeconds)
            .addOnSuccessListener(
                result -> {
                    resultCallback.success();
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(TAG, "Fetch config failed.", exception);
                    resultCallback.error(exception.getMessage());
                }
            );
    }

    public GetValueResult<Boolean> getBoolean(String key) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(key);
        return new GetValueResult<Boolean>(value.asBoolean(), value.getSource());
    }

    public GetValueResult<Double> getNumber(String key) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(key);
        return new GetValueResult<Double>(value.asDouble(), value.getSource());
    }

    public GetValueResult<String> getString(String key) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(key);
        return new GetValueResult<String>(value.asString(), value.getSource());
    }

    public Task<Void> setSettings(@Nullable Integer fetchTimeoutInSeconds, @Nullable Integer minimumFetchIntervalInSeconds) {
        FirebaseRemoteConfigSettings.Builder builder = new FirebaseRemoteConfigSettings.Builder();
        if (fetchTimeoutInSeconds != null) {
            builder.setFetchTimeoutInSeconds(fetchTimeoutInSeconds);
        }
        if (minimumFetchIntervalInSeconds != null) {
            builder.setMinimumFetchIntervalInSeconds(minimumFetchIntervalInSeconds);
        }
        return getFirebaseRemoteConfigInstance().setConfigSettingsAsync(builder.build());
    }

    public void addConfigUpdateListener(@NonNull AddConfigUpdateListenerOptions options, @NonNull NonEmptyResultCallback callback) {
        String callbackId = options.getCallbackId();

        ConfigUpdateListenerRegistration listenerRegistration = getFirebaseRemoteConfigInstance()
            .addOnConfigUpdateListener(
                new ConfigUpdateListener() {
                    @Override
                    public void onUpdate(ConfigUpdate configUpdate) {
                        AddConfigUpdateListenerOptionsCallbackEvent event = new AddConfigUpdateListenerOptionsCallbackEvent(configUpdate);
                        callback.success(event);
                    }

                    @Override
                    public void onError(FirebaseRemoteConfigException error) {
                        callback.error(error);
                    }
                }
            );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void removeConfigUpdateListener(@NonNull RemoveConfigUpdateListenerOptions options) {
        String callbackId = options.getCallbackId();

        ConfigUpdateListenerRegistration listenerRegistration = this.listenerRegistrationMap.get(callbackId);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.remove(callbackId);
    }

    public void removeAllListeners() {
        for (ConfigUpdateListenerRegistration listenerRegistration : this.listenerRegistrationMap.values()) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.clear();
    }

    private com.google.firebase.remoteconfig.FirebaseRemoteConfig getFirebaseRemoteConfigInstance() {
        return com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance();
    }
}
