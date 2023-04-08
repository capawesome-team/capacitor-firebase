package io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces;

public interface GetIdTokenResultCallback {
    void success(String token);
    void error(String message);
}
