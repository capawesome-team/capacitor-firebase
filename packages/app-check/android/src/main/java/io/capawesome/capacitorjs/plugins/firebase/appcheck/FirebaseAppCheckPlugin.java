package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseAppCheck")
public class FirebaseAppCheckPlugin extends Plugin {

    public static final String TAG = "FirebaseAppCheck";
    public static final String ERROR_ENABLED_MISSING = "enabled must be provided.";

    private FirebaseAppCheck implementation = new FirebaseAppCheck();

    @PluginMethod
    public void getToken(PluginCall call) {
        try {
            boolean forceRefresh = call.getBoolean("forceRefresh", false);
            implementation.getToken(
                forceRefresh,
                new GetTokenResultCallback() {
                    @Override
                    public void success(String token, long expireTimeMillis) {
                        JSObject result = new JSObject();
                        result.put("token", token);
                        result.put("expireTimeMillis", expireTimeMillis);
                        call.resolve(result);
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            Boolean debugTokenBoolean = call.getBoolean("debugToken", false);

            boolean hasDebugToken;
            if (Boolean.TRUE.equals(debugTokenBoolean)) {
                hasDebugToken = true;
            } else {
                String debugTokenString = call.getString("debugToken", "");
                if (debugTokenString != null && !debugTokenString.isEmpty()) {
                    hasDebugToken = true;
                } else {
                    Boolean debugOption = call.getBoolean("debug", false);
                    hasDebugToken = Boolean.TRUE.equals(debugOption);
                }
            }

            boolean isTokenAutoRefreshEnabled = call.getBoolean("isTokenAutoRefreshEnabled", false);
            implementation.initialize(hasDebugToken, isTokenAutoRefreshEnabled);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setTokenAutoRefreshEnabled(PluginCall call) {
        try {
            Boolean enabled = call.getBoolean("enabled");
            if (enabled == null) {
                call.reject(ERROR_ENABLED_MISSING);
                return;
            }
            implementation.setTokenAutoRefreshEnabled(enabled);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
