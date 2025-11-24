package app.capgo.capacitor.firebase.storage.classes.results;

import android.net.Uri;
import com.getcapacitor.JSObject;
import app.capgo.capacitor.firebase.storage.interfaces.Result;

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
