package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.NonNull;

public class SignInCanceledException extends Exception {

    public SignInCanceledException(@NonNull String message) {
        super(message);
    }
}
