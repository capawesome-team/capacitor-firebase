package io.capawesome.capacitorjs.plugins.firebase.functions;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Functions")
public class FunctionsPlugin extends Plugin {

    public static final String TAG = "FirebaseFunctions";
    public static final String ERROR_NAME_MISSING = "name must be provided.";
    public static final String ERROR_URL_MISSING = "url must be provided.";

    private Functions implementation = new Functions();

    private FirebaseFirestore implementation;

    public void load() {
        implementation = new FirebaseFirestore(this);
    }

    @PluginMethod
    public void callByName(PluginCall call) {
        JSObject options = call.getData();
        String name = options.getString("name");
        if (name == null) {
            call.reject(ERROR_NAME_MISSING);
            return;
        }
        Object data = options.opt("data");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
