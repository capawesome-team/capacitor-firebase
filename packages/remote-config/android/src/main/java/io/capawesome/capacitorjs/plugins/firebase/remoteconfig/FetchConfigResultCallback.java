package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

public interface FetchConfigResultCallback {
    void success();
    void error(String message);
}
