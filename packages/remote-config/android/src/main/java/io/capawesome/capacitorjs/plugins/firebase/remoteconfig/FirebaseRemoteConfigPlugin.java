package io.capawesome.capacitorjs.plugins.firebase.remoteconfig;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseRemoteConfig")
public class FirebaseRemoteConfigPlugin extends Plugin {

    private FirebaseRemoteConfig implementation = new FirebaseRemoteConfig();

    @PluginMethod
    public void echo(PluginCall call) {
        try {
            
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }
}
