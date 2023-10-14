package io.capawesome.capacitorjs.plugins.firebase.storage.classes.results;

import android.net.Uri;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

public class GetDownloadUrlResult implements Result {

    private Uri uri;

    public GetDownloadUrlResult(Uri uri) {
        this.uri = uri;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("downloadUrl", uri.toString());
        return result;
    }
}
