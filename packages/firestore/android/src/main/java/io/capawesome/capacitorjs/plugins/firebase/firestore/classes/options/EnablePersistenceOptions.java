package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class EnablePersistenceOptions {

    @Nullable
    private final Long cacheSizeBytes;

    public EnablePersistenceOptions(@NonNull PluginCall call) {
        this.cacheSizeBytes = EnablePersistenceOptions.getCacheSizeBytesFromCall(call);
    }

    @Nullable
    public Long getCacheSizeBytes() {
        return cacheSizeBytes;
    }

    @Nullable
    private static Long getCacheSizeBytesFromCall(@NonNull PluginCall call) {
        return call.getLong("cacheSizeBytes");
    }
}
