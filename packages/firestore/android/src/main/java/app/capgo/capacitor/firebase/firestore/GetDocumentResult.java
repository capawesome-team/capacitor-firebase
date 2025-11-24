package app.capgo.capacitor.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.DocumentSnapshot;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import app.capgo.capacitor.firebase.firestore.interfaces.Result;
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
        snapshotResult.put("metadata", FirebaseFirestoreHelper.createSnapshotMetadataResult(documentSnapshot));

        JSObject result = new JSObject();
        result.put("snapshot", snapshotResult);
        return result;
    }
}
