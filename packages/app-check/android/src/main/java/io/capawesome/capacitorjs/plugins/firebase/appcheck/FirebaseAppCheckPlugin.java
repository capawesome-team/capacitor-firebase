package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseAppCheck")
public class FirebaseAppCheckPlugin extends Plugin {

    private FirebaseAppCheck implementation = new FirebaseAppCheck();

    @PluginMethod
    public void echo(PluginCall call) {
        try {
            
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }
}
