package app.capgo.capacitor.firebase.messaging;

public interface GetTokenResultCallback {
    void success(String token);
    void error(String message);
}
