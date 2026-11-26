package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class EnablePersistenceOptions {

    @Nullable
    private final String appName;

    @Nullable
    private final Long cacheSizeBytes;

    public EnablePersistenceOptions(@NonNull PluginCall call) {
        this.cacheSizeBytes = EnablePersistenceOptions.getCacheSizeBytesFromCall(call);
        this.appName = EnablePersistenceOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @Nullable
    public Long getCacheSizeBytes() {
        return cacheSizeBytes;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @Nullable
    private static Long getCacheSizeBytesFromCall(@NonNull PluginCall call) {
        return call.getLong("cacheSizeBytes");
    }
}
