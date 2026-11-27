package io.capawesome.capacitorjs.plugins.firebase.app;

import androidx.annotation.NonNull;
import com.google.firebase.FirebaseOptions;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.options.InitializeAppOptions;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetAppsResult;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetNameResult;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetOptionsResult;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.NonEmptyResultCallback;
import java.util.List;

public class FirebaseApp {

    @NonNull
    private final FirebaseAppPlugin plugin;

    public FirebaseApp(@NonNull FirebaseAppPlugin plugin) {
        this.plugin = plugin;
    }

    public void getApps(@NonNull NonEmptyResultCallback<GetAppsResult> callback) {
        List<com.google.firebase.FirebaseApp> apps = com.google.firebase.FirebaseApp.getApps(plugin.getContext());
        callback.success(new GetAppsResult(apps));
    }

    public void getName(@NonNull NonEmptyResultCallback<GetNameResult> callback) {
        String name = getFirebaseAppInstance().getName();
        callback.success(new GetNameResult(name));
    }

    public void getOptions(@NonNull NonEmptyResultCallback<GetOptionsResult> callback) {
        FirebaseOptions options = getFirebaseAppInstance().getOptions();
        callback.success(new GetOptionsResult(options));
    }

    public void initializeApp(@NonNull InitializeAppOptions options, @NonNull EmptyResultCallback callback) {
        String name = options.getName();
        if (isAppInitialized(name)) {
            callback.error(CustomExceptions.APP_ALREADY_EXISTS);
            return;
        }
        com.google.firebase.FirebaseApp.initializeApp(plugin.getContext(), options.getOptions(), name);
        callback.success();
    }

    private com.google.firebase.FirebaseApp getFirebaseAppInstance() {
        return com.google.firebase.FirebaseApp.getInstance();
    }

    private boolean isAppInitialized(@NonNull String name) {
        for (com.google.firebase.FirebaseApp app : com.google.firebase.FirebaseApp.getApps(plugin.getContext())) {
            if (app.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
