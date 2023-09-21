package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;

public class AddDocumentResult implements Result {

    private String id;

    public AddDocumentResult(String id) {
        this.id = id;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id);
        return result;
    }
}
