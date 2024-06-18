package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.Nullable;

public class CallOptions {
    @Nullable
    private Object data;

    public CallOptions(@Nullable Object data) {
        this.data = data;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
