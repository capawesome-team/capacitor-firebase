package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;

public class GetCollectionGroupResult implements Result {

    private QuerySnapshot querySnapshot;

    public GetCollectionGroupResult(QuerySnapshot querySnapshot) {
        this.querySnapshot = querySnapshot;
    }

    @Override
    public JSObject toJSObject() {
        JSArray snapshotsResult = new JSArray();
        for (QueryDocumentSnapshot document : querySnapshot) {
            JSObject snapshotResult = new JSObject();
            snapshotResult.put("id", document.getId());
            snapshotResult.put("path", document.getReference().getPath());
            snapshotResult.put("data", document.getData());
            snapshotsResult.put(snapshotResult);
        }

        JSObject result = new JSObject();
        result.put("snapshots", snapshotsResult);
        return result;
    }
}
