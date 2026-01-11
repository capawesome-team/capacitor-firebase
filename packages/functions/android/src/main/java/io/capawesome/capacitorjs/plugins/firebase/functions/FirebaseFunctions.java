package io.capawesome.capacitorjs.plugins.firebase.functions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByNameOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByUrlOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.results.CallResult;
import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.NonEmptyResultCallback;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FirebaseFunctions {

    private FirebaseFunctionsPlugin plugin;

    public FirebaseFunctions(FirebaseFunctionsPlugin plugin) {
        this.plugin = plugin;
    }

    public void callByName(@NonNull CallByNameOptions options, @NonNull NonEmptyResultCallback callback) {
        String name = options.getName();
        String region = options.getRegion();
        Object data = options.getData();
        Long timeout = options.getTimeout();

        com.google.firebase.functions.HttpsCallableReference callable = getFirebaseFunctionsInstance(region).getHttpsCallable(name);

        if (timeout != null) {
            callable.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        callable
            .call(data)
            .addOnSuccessListener(task -> {
                CallResult result = new CallResult(task.getData());
                callback.success(result);
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void callByUrl(@NonNull CallByUrlOptions options, @NonNull NonEmptyResultCallback callback) {
        URL url = options.getUrl();
        Object data = options.getData();
        Long timeout = options.getTimeout();

        com.google.firebase.functions.HttpsCallableReference callable = getFirebaseFunctionsInstance(null).getHttpsCallableFromUrl(url);

        if (timeout != null) {
            callable.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        callable
            .call(data)
            .addOnSuccessListener(task -> {
                CallResult result = new CallResult(task.getData());
                callback.success(result);
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void useEmulator(@NonNull String host, int port) {
        getFirebaseFunctionsInstance(null).useEmulator(host, port);
    }

    private com.google.firebase.functions.FirebaseFunctions getFirebaseFunctionsInstance(@Nullable String regionOrCustomDomain) {
        if (regionOrCustomDomain == null) {
            return com.google.firebase.functions.FirebaseFunctions.getInstance();
        } else {
            return com.google.firebase.functions.FirebaseFunctions.getInstance(regionOrCustomDomain);
        }
    }
}
