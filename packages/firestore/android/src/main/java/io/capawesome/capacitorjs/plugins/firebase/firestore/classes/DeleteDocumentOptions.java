package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

public class DeleteDocumentOptions {
    private String reference;

    public DeleteDocumentOptions(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
