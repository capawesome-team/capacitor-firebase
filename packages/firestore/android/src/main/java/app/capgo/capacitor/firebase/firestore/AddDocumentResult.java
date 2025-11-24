package app.capgo.capacitor.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.DocumentReference;
import app.capgo.capacitor.firebase.firestore.interfaces.Result;

public class AddDocumentResult implements Result {

    private DocumentReference documentReference;

    public AddDocumentResult(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    public JSObject toJSObject() {
        JSObject referenceResult = new JSObject();
        referenceResult.put("id", documentReference.getId());
        referenceResult.put("path", documentReference.getPath());

        JSObject result = new JSObject();
        result.put("reference", referenceResult);
        return result;
    }
}
