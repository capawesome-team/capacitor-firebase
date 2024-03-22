package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;

public class FetchSignInMethodsForEmailOptions {

    @NonNull
    private String email;

    public FetchSignInMethodsForEmailOptions(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getEmail() {
        return this.email;
    }
}
