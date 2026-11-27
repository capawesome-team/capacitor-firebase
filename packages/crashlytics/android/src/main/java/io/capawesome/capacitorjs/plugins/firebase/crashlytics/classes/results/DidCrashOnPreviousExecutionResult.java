package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.interfaces.Result;

public class DidCrashOnPreviousExecutionResult implements Result {

    private final boolean crashed;

    public DidCrashOnPreviousExecutionResult(boolean crashed) {
        this.crashed = crashed;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("crashed", crashed);
        return result;
    }
}
