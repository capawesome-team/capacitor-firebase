package app.capgo.capacitor.firebase.authentication.classes.results;

import app.capgo.capacitor.firebase.authentication.interfaces.Result;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
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
