package app.capgo.capacitor.firebase.remoteconfig;

public class GetValueResult<T> {

    T value;
    int source;

    public GetValueResult(T value, int source) {
        this.value = value;
        this.source = source;
    }
}
