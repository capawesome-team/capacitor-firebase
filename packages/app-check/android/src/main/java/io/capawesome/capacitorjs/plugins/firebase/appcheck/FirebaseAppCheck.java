package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import android.util.Log;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

public class FirebaseAppCheck {

    public void getToken(boolean forceRefresh, final GetTokenResultCallback resultCallback) {
        getFirebaseAppCheckInstance().getAppCheckToken(forceRefresh)
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
            getFirebaseAppCheckInstance().installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance(), isTokenAutoRefreshEnabled);
        } else {
            getFirebaseAppCheckInstance().installAppCheckProviderFactory(
                    PlayIntegrityAppCheckProviderFactory.getInstance(),
                    isTokenAutoRefreshEnabled
                );
        }
    }

    public void setTokenAutoRefreshEnabled(boolean enabled) {
        getFirebaseAppCheckInstance().setTokenAutoRefreshEnabled(enabled);
    }

    private com.google.firebase.appcheck.FirebaseAppCheck getFirebaseAppCheckInstance() {
        return com.google.firebase.appcheck.FirebaseAppCheck.getInstance();
    }
}
