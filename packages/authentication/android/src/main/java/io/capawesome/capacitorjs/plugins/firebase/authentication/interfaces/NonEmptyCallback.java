package io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyCallback<T> extends ErrorCallback {
    void success(@NonNull T result);
}
