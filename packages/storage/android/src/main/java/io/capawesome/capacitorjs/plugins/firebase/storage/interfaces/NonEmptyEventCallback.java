package io.capawesome.capacitorjs.plugins.firebase.storage.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyEventCallback<T extends Result> extends ErrorCallback {
    void success(@NonNull T result);
    void release();
}
