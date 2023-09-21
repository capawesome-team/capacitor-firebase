package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

public class GetDocumentOptions {
    private String reference;

    public GetDocumentOptions(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
