package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

public class GetDocumentOptions {

    private String reference;

    public GetDocumentOptions(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
