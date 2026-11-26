package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.CustomExceptions;
import java.net.URL;

public class CallByUrlOptions extends CallOptions {

    @NonNull
    private final URL url;

    public CallByUrlOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the url before super(call) reads the timeout, so a missing url is reported first.
        this(call, CallByUrlOptions.getUrlFromCall(call));
    }

    private CallByUrlOptions(@NonNull PluginCall call, @NonNull String url) throws Exception {
        super(call);
        // ALWAYS parse the url after super(call), so an invalid timeout is reported before an invalid url.
        this.url = new URL(url);
    }

    @NonNull
    public URL getUrl() {
        return url;
    }

    @NonNull
    private static String getUrlFromCall(@NonNull PluginCall call) throws Exception {
        // NEVER switch to call.getString. This read also accepts a number as the url.
        String url = call.getData().getString("url");
        if (url == null) {
            throw CustomExceptions.URL_MISSING;
        }
        return url;
    }
}
