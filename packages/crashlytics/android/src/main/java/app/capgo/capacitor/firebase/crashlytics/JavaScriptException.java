package app.capgo.capacitor.firebase.crashlytics;

import com.getcapacitor.JSArray;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JavaScriptException extends Exception {

    public JavaScriptException(String message) {
        super(message);
    }

    public JavaScriptException(String message, JSArray stackTrace) throws JSONException {
        this(message);
        this.handleStacktrace(stackTrace);
    }

    private void handleStacktrace(JSArray stackTrace) throws JSONException {
        if (stackTrace == null) {
            return;
        }

        StackTraceElement[] trace = new StackTraceElement[stackTrace.length()];
        for (int i = 0; i < stackTrace.length(); i++) {
            JSONObject elem = stackTrace.getJSONObject(i);

            trace[i] = new StackTraceElement(
                "",
                elem.optString("functionName", "(anonymous function)"),
                elem.optString("fileName", "(unknown file)"),
                elem.optInt("lineNumber", -1)
            );
        }

        this.setStackTrace(trace);
    }
}
