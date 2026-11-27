package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.interfaces.Result;

public class GetInfoResult implements Result {

    private final int lastFetchStatus;

    private final long lastFetchTime;

    public GetInfoResult(long lastFetchTime, int sdkFetchStatus) {
        this.lastFetchTime = lastFetchTime;
        this.lastFetchStatus = mapFetchStatus(sdkFetchStatus);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("lastFetchTime", lastFetchTime);
        result.put("lastFetchStatus", lastFetchStatus);
        return result;
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
