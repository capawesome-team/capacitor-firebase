package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;

public class CallByUrlOptions extends CallOptions {
    @NonNull
    private String url;

    public CallByUrlOptions(@NonNull String url, Object data) {
        super(data);
        this.url = url;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
