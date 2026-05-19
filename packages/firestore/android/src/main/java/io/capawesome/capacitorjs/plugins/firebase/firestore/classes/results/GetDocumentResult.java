package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.DocumentSnapshot;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;
import org.json.JSONObject;

public class GetDocumentResult implements Result {

    private final DocumentSnapshot documentSnapshot;
    private final DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior;

    public GetDocumentResult(DocumentSnapshot documentSnapshot) {
        this(documentSnapshot, DocumentSnapshot.ServerTimestampBehavior.NONE);
    }

    public GetDocumentResult(DocumentSnapshot documentSnapshot, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        this.documentSnapshot = documentSnapshot;
        this.serverTimestampBehavior = serverTimestampBehavior;
    }

    public JSObject toJSObject() {
        Object snapshotDataResult = FirebaseFirestoreHelper.createJSObjectFromMap(documentSnapshot.getData(serverTimestampBehavior));

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
