package io.capawesome.capacitorjs.plugins.firebase.functions;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByNameOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByUrlOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.Result;

@CapacitorPlugin(name = "FirebaseFunctions")
public class FirebaseFunctionsPlugin extends Plugin {

    public static final String TAG = "FirebaseFunctions";
    public static final String ERROR_NAME_MISSING = "name must be provided.";
    public static final String ERROR_URL_MISSING = "url must be provided.";

    private FirebaseFunctions implementation;

    public void load() {
        implementation = new FirebaseFunctions(this);
    }

    @PluginMethod
    public void callByName(PluginCall call) {
        try {
            JSObject json = call.getData();
            String name = json.getString("name", null);
            if (name == null) {
                call.reject(ERROR_NAME_MISSING);
                return;
            }
            String region = json.getString("region", null);
            Object data = json.opt("data");

            CallByNameOptions options = new CallByNameOptions(name, region, data);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.callByName(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void callByUrl(PluginCall call) {
        try {
            JSObject json = call.getData();
            String url = json.getString("url");
            if (url == null) {
                call.reject(ERROR_URL_MISSING);
                return;
            }
            Object data = json.opt("data");

            CallByUrlOptions options = new CallByUrlOptions(url, data);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.callByUrl(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
