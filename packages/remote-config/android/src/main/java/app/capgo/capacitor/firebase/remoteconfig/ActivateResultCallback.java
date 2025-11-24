package app.capgo.capacitor.firebase.remoteconfig;

public interface ActivateResultCallback {
    void success(boolean success);
    void error(String message);
}
