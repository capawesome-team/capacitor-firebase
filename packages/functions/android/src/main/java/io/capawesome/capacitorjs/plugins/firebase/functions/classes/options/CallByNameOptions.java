package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CallByNameOptions extends CallOptions {

    @NonNull
    private String name;

    @Nullable
    private String region;

    public CallByNameOptions(@NonNull String name, @Nullable String region,  Object data) {
        super(data);
        this.name = name;
        this.region = region;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getRegion() {
        return region;
    }
}
