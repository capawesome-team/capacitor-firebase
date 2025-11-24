package app.capgo.capacitor.firebase.firestore.classes.options;

public class RemoveSnapshotListenerOptions {

    private String callbackId;

    public RemoveSnapshotListenerOptions(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
