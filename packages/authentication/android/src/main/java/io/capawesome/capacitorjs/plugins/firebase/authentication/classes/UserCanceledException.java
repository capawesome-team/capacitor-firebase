package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.NonNull;

public class UserCanceledException extends Exception {

    public UserCanceledException(@NonNull String message) {
        super(message);
    }
}
