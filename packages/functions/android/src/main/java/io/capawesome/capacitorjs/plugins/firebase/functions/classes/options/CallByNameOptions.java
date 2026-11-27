package io.capawesome.capacitorjs.plugins.firebase.functions.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.functions.classes.CustomExceptions;

public class CallByNameOptions extends CallOptions {

    @NonNull
    private final String name;

    @Nullable
    private final String region;

    public CallByNameOptions(@NonNull PluginCall call) throws Exception {
        // ALWAYS read the name before super(call) reads the timeout, so a missing name is reported first.
        this(call, CallByNameOptions.getNameFromCall(call));
    }

    private CallByNameOptions(@NonNull PluginCall call, @NonNull String name) throws Exception {
        super(call);
        this.name = name;
        this.region = CallByNameOptions.getRegionFromCall(call);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    @NonNull
    private static String getNameFromCall(@NonNull PluginCall call) throws Exception {
        // NEVER switch to call.getString. This read also accepts a number such as 123 as the name.
        String name = call.getData().getString("name", null);
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        return name;
    }

    @Nullable
    private static String getRegionFromCall(@NonNull PluginCall call) {
        // NEVER switch to call.getString. This read also accepts a number as the region.
        return call.getData().getString("region", null);
    }
}
