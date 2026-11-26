package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class UpdateProfileOptions {

    @Nullable
    private final String appName;

    @Nullable
    private final String displayName;

    @Nullable
    private final String photoUrl;

    public UpdateProfileOptions(@NonNull PluginCall call) {
        this.displayName = UpdateProfileOptions.getDisplayNameFromCall(call);
        this.photoUrl = UpdateProfileOptions.getPhotoUrlFromCall(call);
        this.appName = UpdateProfileOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @Nullable
    private static String getDisplayNameFromCall(@NonNull PluginCall call) {
        return call.getString("displayName");
    }

    @Nullable
    private static String getPhotoUrlFromCall(@NonNull PluginCall call) {
        return call.getString("photoUrl");
    }
}
