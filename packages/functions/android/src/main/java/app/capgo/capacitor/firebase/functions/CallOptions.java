package app.capgo.capacitor.firebase.functions.classes.options;

import androidx.annotation.Nullable;

public class CallOptions {

    @Nullable
    private Object data;

    @Nullable
    private Long timeout;

    public CallOptions(@Nullable Object data, @Nullable Long timeout) {
        this.data = data;
        this.timeout = timeout;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Nullable
    public Long getTimeout() {
        return timeout;
    }
}
