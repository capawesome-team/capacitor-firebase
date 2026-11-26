package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.FirebaseUser;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;
import org.json.JSONObject;

public class AuthStateChangeEvent implements Result {

    @Nullable
    private final FirebaseUser user;

    public AuthStateChangeEvent(@Nullable FirebaseUser user) {
        this.user = user;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(user);
        JSObject result = new JSObject();
        result.put("user", (userResult == null ? JSONObject.NULL : userResult));
        return result;
    }
}
