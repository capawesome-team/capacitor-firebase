package dev.robingenz.capacitorjs.plugins.firebase.messaging;

public interface GetTokenResultCallback {
    void success(String token);
    void error(String message);
}
