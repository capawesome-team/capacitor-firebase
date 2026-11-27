package io.capawesome.capacitorjs.plugins.firebase.performance.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends ErrorCallback {
    void success(@NonNull T result);
}
