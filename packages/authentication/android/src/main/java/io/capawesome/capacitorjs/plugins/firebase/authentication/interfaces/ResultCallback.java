package io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces;

public interface ResultCallback<T extends Result> {
    void success(T result);
    void error(Exception exception);
}
