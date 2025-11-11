package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

public class GetInfoResult {

    long lastFetchTime;
    int lastFetchStatus;

    public GetInfoResult(long lastFetchTime, int sdkFetchStatus) {
        this.lastFetchTime = lastFetchTime;
        this.lastFetchStatus = mapFetchStatus(sdkFetchStatus);
    }

    private int mapFetchStatus(int sdkStatus) {
        return switch (sdkStatus) {
            case 0 -> 0;
            case -1 -> 1;
            case 1 -> 2;
            case 2 -> 3;
            default -> 2;
        };
    }
}
