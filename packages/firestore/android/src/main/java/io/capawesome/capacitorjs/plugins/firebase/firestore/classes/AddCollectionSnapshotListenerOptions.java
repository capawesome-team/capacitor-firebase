package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

public class AddCollectionSnapshotListenerOptions {

    private String reference;
    private String callbackId;

    public AddCollectionSnapshotListenerOptions(String reference, String callbackId) {
        this.reference = reference;
        this.callbackId = callbackId;
    }

    public String getReference() {
        return reference;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
