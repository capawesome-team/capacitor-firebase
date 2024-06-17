package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;

public class RevokeAccessTokenOptions {

    @NonNull
    private String token;

    public RevokeAccessTokenOptions(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return this.token;
    }
}
