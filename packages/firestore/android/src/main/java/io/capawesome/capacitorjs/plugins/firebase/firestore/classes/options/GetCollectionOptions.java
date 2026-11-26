package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class GetCollectionOptions {

    @Nullable
    private final String appName;

    @Nullable
    private final JSObject compositeFilter;

    @Nullable
    private final JSArray queryConstraints;

    @NonNull
    private final String reference;

    public GetCollectionOptions(@NonNull PluginCall call) throws Exception {
        this.reference = GetCollectionOptions.getReferenceFromCall(call);
        this.compositeFilter = GetCollectionOptions.getCompositeFilterFromCall(call);
        this.queryConstraints = GetCollectionOptions.getQueryConstraintsFromCall(call);
        this.appName = GetCollectionOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @Nullable
    public JSObject getCompositeFilter() {
        return compositeFilter;
    }

    @Nullable
    public JSArray getQueryConstraints() {
        return queryConstraints;
    }

    @NonNull
    public String getReference() {
        return reference;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName", null);
    }

    @Nullable
    private static JSObject getCompositeFilterFromCall(@NonNull PluginCall call) {
        return call.getObject("compositeFilter", null);
    }

    @Nullable
    private static JSArray getQueryConstraintsFromCall(@NonNull PluginCall call) {
        return call.getArray("queryConstraints", null);
    }

    @NonNull
    private static String getReferenceFromCall(@NonNull PluginCall call) throws Exception {
        String reference = call.getString("reference");
        if (reference == null) {
            throw CustomExceptions.REFERENCE_MISSING;
        }
        return reference;
    }
}
