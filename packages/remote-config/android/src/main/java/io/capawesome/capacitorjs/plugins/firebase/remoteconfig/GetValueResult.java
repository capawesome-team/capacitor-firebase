package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

public class GetValueResult<T> {

    T value;
    int source;

    public GetValueResult(T value, int source) {
        this.value = value;
        this.source = source;
    }
}
