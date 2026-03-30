package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import android.net.Uri;

public class DownloadFileOptions {

    private String path;
    private Uri uri;
    private String callbackId;

    public DownloadFileOptions(String path, String uri, String callbackId) {
        this.path = path;
        this.uri = Uri.parse(uri);
        this.callbackId = callbackId;
    }

    public String getPath() {
        return path;
    }

    public Uri getUri() {
        return uri;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
