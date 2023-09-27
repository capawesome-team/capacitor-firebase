package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.DocumentSnapshot;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;

public class GetDocumentResult implements Result {

    private DocumentSnapshot documentSnapshot;

    public GetDocumentResult(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    public JSObject toJSObject() {
        JSObject snapshotResult = new JSObject();
        snapshotResult.put("id", documentSnapshot.getId());
        snapshotResult.put("path", documentSnapshot.getReference().getPath());
        snapshotResult.put("data", documentSnapshot.getData());

        JSObject result = new JSObject();
        result.put("snapshot", snapshotResult);
        return result;
    }
}
