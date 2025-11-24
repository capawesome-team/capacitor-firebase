package app.capgo.capacitor.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import app.capgo.capacitor.firebase.firestore.interfaces.Result;

public class GetCountFromServerResult implements Result {

    private final long count;

    public GetCountFromServerResult(long count) {
        this.count = count;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("count", count);
        return result;
    }
}
