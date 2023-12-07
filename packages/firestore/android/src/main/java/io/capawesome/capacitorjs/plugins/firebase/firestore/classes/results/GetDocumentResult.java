package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.DocumentSnapshot;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;
import org.json.JSONObject;

public class GetDocumentResult implements Result {

    private DocumentSnapshot documentSnapshot;

    public GetDocumentResult(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    public JSObject toJSObject() {
        Object snapshotDataResult = FirebaseFirestoreHelper.createJSObjectFromMap(documentSnapshot.getData());

        JSObject snapshotResult = new JSObject();
        snapshotResult.put("id", documentSnapshot.getId());
        snapshotResult.put("path", documentSnapshot.getReference().getPath());
        if (snapshotDataResult == null) {
            snapshotResult.put("data", JSONObject.NULL);
        } else {
            snapshotResult.put("data", snapshotDataResult);
        }

        JSObject result = new JSObject();
        result.put("snapshot", snapshotResult);
        return result;
    }
}
