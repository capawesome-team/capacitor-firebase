package app.capgo.capacitor.firebase.remoteconfig.classes.options;

public class RemoveConfigUpdateListenerOptions {

    private String callbackId;

    public RemoveConfigUpdateListenerOptions(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
