package app.capgo.capacitor.firebase.firestore.classes.options;

public class DeleteDocumentOptions {

    private String reference;

    public DeleteDocumentOptions(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
