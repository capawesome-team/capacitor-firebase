package io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces;

public interface ResultCallback {
    void success(Result result);
    void error(Exception exception);
}
