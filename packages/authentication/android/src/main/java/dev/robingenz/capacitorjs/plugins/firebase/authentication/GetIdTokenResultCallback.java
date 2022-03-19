package dev.robingenz.capacitorjs.plugins.firebase.authentication;

public interface GetIdTokenResultCallback {
    void success(String token);
    void error(String message);
}
