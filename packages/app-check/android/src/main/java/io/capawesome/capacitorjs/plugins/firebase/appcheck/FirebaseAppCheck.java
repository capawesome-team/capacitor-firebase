package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import androidx.annotation.NonNull;
import com.google.firebase.appcheck.AppCheckToken;
import com.google.firebase.appcheck.FirebaseAppCheck.AppCheckListener;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.events.TokenChangedEvent;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.GetTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.SetTokenAutoRefreshEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.results.GetTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.NonEmptyResultCallback;

public class FirebaseAppCheck {

    @NonNull
    private final AppCheckListener appCheckListener;

    @NonNull
    private final FirebaseAppCheckPlugin plugin;

    public FirebaseAppCheck(@NonNull FirebaseAppCheckPlugin plugin) {
        this.plugin = plugin;
        this.appCheckListener = this::handleTokenChanged;
        getFirebaseAppCheckInstance().addAppCheckListener(appCheckListener);
    }

    public void destroy() {
        getFirebaseAppCheckInstance().removeAppCheckListener(appCheckListener);
    }

    public void getToken(@NonNull GetTokenOptions options, @NonNull NonEmptyResultCallback<GetTokenResult> callback) {
        getFirebaseAppCheckInstance()
            .getAppCheckToken(options.getForceRefresh())
            .addOnSuccessListener(appCheckToken -> {
                GetTokenResult result = new GetTokenResult(appCheckToken.getToken(), appCheckToken.getExpireTimeMillis());
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyResultCallback callback) {
        if (options.getDebug()) {
            getFirebaseAppCheckInstance().installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance(),
                options.isTokenAutoRefreshEnabled()
            );
        } else {
            getFirebaseAppCheckInstance().installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance(),
                options.isTokenAutoRefreshEnabled()
            );
        }
        callback.success();
    }

    public void setTokenAutoRefreshEnabled(@NonNull SetTokenAutoRefreshEnabledOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseAppCheckInstance().setTokenAutoRefreshEnabled(options.getEnabled());
        callback.success();
    }

    private com.google.firebase.appcheck.FirebaseAppCheck getFirebaseAppCheckInstance() {
        return com.google.firebase.appcheck.FirebaseAppCheck.getInstance();
    }

    private void handleTokenChanged(@NonNull AppCheckToken token) {
        TokenChangedEvent event = new TokenChangedEvent(token.getToken());
        plugin.notifyTokenChangedListeners(event);
    }
}
