package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;
import java.util.List;

public class FetchSignInMethodsForEmailResult implements Result {

    private List<String> signInMethods;

    public FetchSignInMethodsForEmailResult(List<String> signInMethods) {
        this.signInMethods = signInMethods;
    }

    public JSObject toJSObject() {
        JSArray signInMethodsResult = new JSArray();
        for (String signInMethod : signInMethods) {
            signInMethodsResult.put(signInMethod);
        }

        JSObject result = new JSObject();
        result.put("signInMethods", signInMethodsResult);
        return result;
    }
}
