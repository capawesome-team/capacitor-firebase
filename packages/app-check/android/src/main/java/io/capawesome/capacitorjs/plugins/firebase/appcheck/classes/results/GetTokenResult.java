package io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.Result;

public class GetTokenResult implements Result {

    private final long expireTimeMillis;

    @NonNull
    private final String token;

    public GetTokenResult(@NonNull String token, long expireTimeMillis) {
        this.token = token;
        this.expireTimeMillis = expireTimeMillis;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("token", token);
        result.put("expireTimeMillis", expireTimeMillis);
        return result;
    }
}
