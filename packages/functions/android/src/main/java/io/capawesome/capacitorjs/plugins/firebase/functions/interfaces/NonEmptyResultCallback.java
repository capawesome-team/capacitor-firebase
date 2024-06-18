package io.capawesome.capacitorjs.plugins.firebase.functions.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends ResultCallback {
    void success(@NonNull T result);
}
