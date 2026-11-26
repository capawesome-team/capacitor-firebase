package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import org.json.JSONException;

public class CallOptions {

    @Nullable
    private final Object data;

    @Nullable
    private final Long timeout;

    public CallOptions(@NonNull PluginCall call) throws Exception {
        this.data = CallOptions.getDataFromCall(call);
        this.timeout = CallOptions.getTimeoutFromCall(call);
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Nullable
    public Long getTimeout() {
        return timeout;
    }

    @Nullable
    private static Object getDataFromCall(@NonNull PluginCall call) {
        return call.getData().opt("data");
    }

    @Nullable
    private static Long getTimeoutFromCall(@NonNull PluginCall call) throws JSONException {
        JSObject json = call.getData();
        // NEVER switch to call.getLong. This read also accepts a text such as "1500" and throws for null.
        return json.has("timeout") ? json.getLong("timeout") : null;
    }
}
