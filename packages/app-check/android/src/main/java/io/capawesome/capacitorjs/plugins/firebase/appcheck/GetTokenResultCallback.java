package io.capawesome.capacitorjs.plugins.firebase.appcheck;

public interface GetTokenResultCallback {
    void success(String token, long expireTimeMillis);
    void error(String message);
}
