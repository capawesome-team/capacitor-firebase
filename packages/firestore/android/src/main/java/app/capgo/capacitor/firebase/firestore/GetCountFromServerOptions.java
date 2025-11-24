package app.capgo.capacitor.firebase.firestore.classes.options;

import androidx.annotation.NonNull;

public class GetCountFromServerOptions {

    @NonNull
    private final String reference;

    public GetCountFromServerOptions(@NonNull String reference) {
        this.reference = reference;
    }

    @NonNull
    public String getReference() {
        return reference;
    }
}
