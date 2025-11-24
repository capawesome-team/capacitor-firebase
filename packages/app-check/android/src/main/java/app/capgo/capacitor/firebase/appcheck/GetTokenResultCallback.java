package app.capgo.capacitor.firebase.appcheck;

public interface GetTokenResultCallback {
    void success(String token, long expireTimeMillis);
    void error(String message);
}
