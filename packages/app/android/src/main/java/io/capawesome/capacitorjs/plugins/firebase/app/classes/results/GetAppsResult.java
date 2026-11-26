package io.capawesome.capacitorjs.plugins.firebase.app.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.Result;
import java.util.List;

public class GetAppsResult implements Result {

    @NonNull
    private final List<com.google.firebase.FirebaseApp> apps;

    public GetAppsResult(@NonNull List<com.google.firebase.FirebaseApp> apps) {
        this.apps = apps;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray appsResult = new JSArray();
        for (com.google.firebase.FirebaseApp app : apps) {
            JSObject appResult = new JSObject();
            appResult.put("name", app.getName());
            appsResult.put(appResult);
        }
        JSObject result = new JSObject();
        result.put("apps", appsResult);
        return result;
    }
}
