package io.capawesome.capacitorjs.plugins.firebase.functions;

import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByNameOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.options.CallByUrlOptions;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.results.CallResult;
import io.capawesome.capacitorjs.plugins.firebase.functions.interfaces.NonEmptyResultCallback;

public class FirebaseFunctions {

    private FirebaseFunctionsPlugin plugin;

    public FirebaseFunctions(FirebaseFunctionsPlugin plugin) {
        this.plugin = plugin;
    }

    public void callByName(@NonNull CallByNameOptions options, @NonNull NonEmptyResultCallback callback) {
        String name = options.getName();
        Object data = options.getData();

        getFirebaseFunctionsInstance()
            .getHttpsCallable(name)
            .call(data)
            .addOnSuccessListener(
                task -> {
                    CallResult result = new CallResult(task.getData());
                    callback.success(result);
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public void callByUrl(@NonNull CallByUrlOptions options, @NonNull NonEmptyResultCallback callback) {
        String url = options.getUrl();
        Object data = options.getData();

        getFirebaseFunctionsInstance()
            .getHttpsCallable(url)
            .call(data)
            .addOnSuccessListener(
                task -> {
                    CallResult result = new CallResult(task.getData());
                    callback.success(result);
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    private com.google.firebase.functions.FirebaseFunctions getFirebaseFunctionsInstance() {
        return com.google.firebase.functions.FirebaseFunctions.getInstance();
    }
}
