package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

public interface ActivateResultCallback {
    void success(boolean success);
    void error(String message);
}
