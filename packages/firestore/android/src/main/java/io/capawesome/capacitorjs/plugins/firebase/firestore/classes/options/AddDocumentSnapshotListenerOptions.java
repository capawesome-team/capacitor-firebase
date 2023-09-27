package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

public class AddDocumentSnapshotListenerOptions {

    private String reference;
    private String callbackId;

    public AddDocumentSnapshotListenerOptions(String reference, String callbackId) {
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
