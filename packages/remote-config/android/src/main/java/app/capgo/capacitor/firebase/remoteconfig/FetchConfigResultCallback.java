package app.capgo.capacitor.firebase.remoteconfig;

public interface FetchConfigResultCallback {
    void success();
    void error(String message);
}
