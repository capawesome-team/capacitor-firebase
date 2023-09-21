package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends ResultCallback {
    void success(@NonNull T result);
}