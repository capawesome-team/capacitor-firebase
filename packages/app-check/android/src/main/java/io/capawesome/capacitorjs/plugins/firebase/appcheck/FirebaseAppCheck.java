package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import android.util.Log;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

public class FirebaseAppCheck {

    private final com.google.firebase.appcheck.FirebaseAppCheck appCheckInstance;

    public FirebaseAppCheck() {
        this.appCheckInstance = com.google.firebase.appcheck.FirebaseAppCheck.getInstance();
    }

    public void getToken(boolean forceRefresh, final GetTokenResultCallback resultCallback) {
        this.appCheckInstance.getAppCheckToken(forceRefresh)
            .addOnSuccessListener(
                appCheckToken -> {
                    resultCallback.success(appCheckToken.getToken(), appCheckToken.getExpireTimeMillis());
                }
            )
            .addOnFailureListener(
                exception -> {
                    Log.w(FirebaseAppCheckPlugin.TAG, "Get App Check token failed.", exception);
                    resultCallback.error(exception.getMessage());
                }
            );
    }

    public void initialize(boolean debug, boolean isTokenAutoRefreshEnabled) {
        if (debug) {
            this.appCheckInstance.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance(), isTokenAutoRefreshEnabled);
        } else {
            this.appCheckInstance.installAppCheckProviderFactory(
                    PlayIntegrityAppCheckProviderFactory.getInstance(),
                    isTokenAutoRefreshEnabled
                );
        }
    }

    public void setTokenAutoRefreshEnabled(boolean enabled) {
        this.appCheckInstance.setTokenAutoRefreshEnabled(enabled);
    }
}
