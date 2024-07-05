package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

public class CallByUrlOptions extends CallOptions {

    @NonNull
    private URL url;

    public CallByUrlOptions(@NonNull String url, Object data) throws MalformedURLException {
        super(data);
        this.url = new URL(url);
    }

    @NonNull
    public URL getUrl() {
        return url;
    }
}
