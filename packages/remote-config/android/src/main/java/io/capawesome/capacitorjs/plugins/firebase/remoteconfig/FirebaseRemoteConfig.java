package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import androidx.annotation.NonNull;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration;
import com.google.firebase.remoteconfig.CustomSignals;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.events.AddConfigUpdateListenerOptionsCallbackEvent;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.AddConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.FetchConfigOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.GetOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.RemoveConfigUpdateListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetCustomSignalsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetDefaultsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options.SetSettingsOptions;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetAllResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetBooleanResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetNumberResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results.GetStringResult;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.NonEmptyResultCallback;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class FirebaseRemoteConfig {

    private final Map<String, ConfigUpdateListenerRegistration> listenerRegistrationMap = new HashMap<>();
    private final FirebaseRemoteConfigPlugin plugin;

    public FirebaseRemoteConfig(FirebaseRemoteConfigPlugin plugin) {
        this.plugin = plugin;
    }

    public void activate(@NonNull EmptyResultCallback callback) {
        getFirebaseRemoteConfigInstance()
            .activate()
            .addOnSuccessListener(result -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void addConfigUpdateListener(
        @NonNull AddConfigUpdateListenerOptions options,
        @NonNull NonEmptyResultCallback<AddConfigUpdateListenerOptionsCallbackEvent> callback
    ) {
        String callbackId = options.getCallbackId();

        ConfigUpdateListenerRegistration listenerRegistration = getFirebaseRemoteConfigInstance().addOnConfigUpdateListener(
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

    public void fetchAndActivate(@NonNull EmptyResultCallback callback) {
        getFirebaseRemoteConfigInstance()
            .fetchAndActivate()
            .addOnSuccessListener(result -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void fetchConfig(@NonNull FetchConfigOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseRemoteConfigInstance()
            .fetch(options.getMinimumFetchIntervalInSeconds())
            .addOnSuccessListener(result -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getAll(@NonNull NonEmptyResultCallback<GetAllResult> callback) {
        Map<String, FirebaseRemoteConfigValue> values = getFirebaseRemoteConfigInstance().getAll();
        GetAllResult result = new GetAllResult(values);
        callback.success(result);
    }

    public void getBoolean(@NonNull GetOptions options, @NonNull NonEmptyResultCallback<GetBooleanResult> callback) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(options.getKey());
        GetBooleanResult result = new GetBooleanResult(value.asBoolean(), value.getSource());
        callback.success(result);
    }

    public void getInfo(@NonNull NonEmptyResultCallback<GetInfoResult> callback) {
        FirebaseRemoteConfigInfo info = getFirebaseRemoteConfigInstance().getInfo();
        long lastFetchTime = info.getFetchTimeMillis();
        int lastFetchStatus = info.getLastFetchStatus();

        GetInfoResult result = new GetInfoResult(lastFetchTime, lastFetchStatus);
        callback.success(result);
    }

    public void getNumber(@NonNull GetOptions options, @NonNull NonEmptyResultCallback<GetNumberResult> callback) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(options.getKey());
        GetNumberResult result = new GetNumberResult(value.asDouble(), value.getSource());
        callback.success(result);
    }

    public void getString(@NonNull GetOptions options, @NonNull NonEmptyResultCallback<GetStringResult> callback) {
        FirebaseRemoteConfigValue value = getFirebaseRemoteConfigInstance().getValue(options.getKey());
        GetStringResult result = new GetStringResult(value.asString(), value.getSource());
        callback.success(result);
    }

    public void removeAllListeners(@NonNull EmptyResultCallback callback) {
        for (ConfigUpdateListenerRegistration listenerRegistration : this.listenerRegistrationMap.values()) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.clear();
        callback.success();
    }

    public void removeConfigUpdateListener(@NonNull RemoveConfigUpdateListenerOptions options, @NonNull EmptyResultCallback callback) {
        String callbackId = options.getCallbackId();

        ConfigUpdateListenerRegistration listenerRegistration = this.listenerRegistrationMap.get(callbackId);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.remove(callbackId);
        callback.success();
    }

    public void setCustomSignals(@NonNull SetCustomSignalsOptions options, @NonNull EmptyResultCallback callback) {
        CustomSignals.Builder builder = new CustomSignals.Builder();
        for (Map.Entry<String, Object> entry : options.getCustomSignals().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || value == JSONObject.NULL) {
                builder.put(key, (String) null);
            } else if (value instanceof String) {
                builder.put(key, (String) value);
            } else if (value instanceof Integer || value instanceof Long) {
                builder.put(key, ((Number) value).longValue());
            } else if (value instanceof Number) {
                builder.put(key, ((Number) value).doubleValue());
            } else {
                throw new IllegalArgumentException("Unsupported value type for key: " + key);
            }
        }
        getFirebaseRemoteConfigInstance()
            .setCustomSignals(builder.build())
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.success();
                } else {
                    Exception exception = task.getException();
                    callback.error(exception == null ? CustomExceptions.SET_CUSTOM_SIGNALS_FAILED : exception);
                }
            });
    }

    public void setDefaults(@NonNull SetDefaultsOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseRemoteConfigInstance()
            .setDefaultsAsync(options.getDefaults())
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.success();
                } else {
                    Exception exception = task.getException();
                    callback.error(exception == null ? CustomExceptions.SET_DEFAULTS_FAILED : exception);
                }
            });
    }

    public void setSettings(@NonNull SetSettingsOptions options, @NonNull EmptyResultCallback callback) {
        FirebaseRemoteConfigSettings.Builder builder = new FirebaseRemoteConfigSettings.Builder();
        builder.setFetchTimeoutInSeconds(options.getFetchTimeoutInSeconds());
        builder.setMinimumFetchIntervalInSeconds(options.getMinimumFetchIntervalInSeconds());
        getFirebaseRemoteConfigInstance()
            .setConfigSettingsAsync(builder.build())
            .addOnCompleteListener(task -> callback.success());
    }

    private com.google.firebase.remoteconfig.FirebaseRemoteConfig getFirebaseRemoteConfigInstance() {
        return com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance();
    }
}
