package app.capgo.capacitor.firebase.authentication.classes;

import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.authentication.interfaces.Result;
import com.getcapacitor.JSObject;

public class GetIdTokenResult implements Result {

    @Nullable
    private String token;

    public GetIdTokenResult(@Nullable String token) {
        this.token = token;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("token", token);
        return result;
    }
}
