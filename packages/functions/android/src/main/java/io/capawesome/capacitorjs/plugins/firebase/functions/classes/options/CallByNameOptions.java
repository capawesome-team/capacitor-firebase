package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;

public class CallByNameOptions extends CallOptions {
    @NonNull
    private String name;

    public CallByNameOptions(@NonNull String name, Object data) {
        super(data);
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
