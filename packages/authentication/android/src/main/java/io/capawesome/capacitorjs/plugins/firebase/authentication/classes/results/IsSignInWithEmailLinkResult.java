package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;

public class IsSignInWithEmailLinkResult implements Result {

    private final boolean isSignInWithEmailLink;

    public IsSignInWithEmailLinkResult(boolean isSignInWithEmailLink) {
        this.isSignInWithEmailLink = isSignInWithEmailLink;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("isSignInWithEmailLink", isSignInWithEmailLink);
        return result;
    }
}
