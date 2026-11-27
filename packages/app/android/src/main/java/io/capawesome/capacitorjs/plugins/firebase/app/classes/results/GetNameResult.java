package io.capawesome.capacitorjs.plugins.firebase.app.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.Result;

public class GetNameResult implements Result {

    @NonNull
    private final String name;

    public GetNameResult(@NonNull String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("name", name);
        return result;
    }
}
