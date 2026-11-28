package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.options;

import android.app.NotificationManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.messaging.classes.CustomExceptions;

public class CreateChannelOptions {

    @NonNull
    private final String description;

    @NonNull
    private final String id;

    private final int importance;

    @Nullable
    private final String lightColor;

    private final boolean lights;

    @NonNull
    private final String name;

    @Nullable
    private final String sound;

    private final boolean vibration;

    private final int visibility;

    public CreateChannelOptions(@NonNull PluginCall call) throws Exception {
        this.id = CreateChannelOptions.getIdFromCall(call);
        this.name = CreateChannelOptions.getNameFromCall(call);
        this.importance = CreateChannelOptions.getImportanceFromCall(call);
        this.description = CreateChannelOptions.getDescriptionFromCall(call);
        this.visibility = CreateChannelOptions.getVisibilityFromCall(call);
        this.vibration = CreateChannelOptions.getVibrationFromCall(call);
        this.lights = CreateChannelOptions.getLightsFromCall(call);
        this.lightColor = CreateChannelOptions.getLightColorFromCall(call);
        this.sound = CreateChannelOptions.getSoundFromCall(call);
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getImportance() {
        return importance;
    }

    @Nullable
    public String getLightColor() {
        return lightColor;
    }

    public boolean getLights() {
        return lights;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getSound() {
        return sound;
    }

    public boolean getVibration() {
        return vibration;
    }

    public int getVisibility() {
        return visibility;
    }

    @NonNull
    private static String getDescriptionFromCall(@NonNull PluginCall call) {
        return call.getString("description", "");
    }

    @NonNull
    private static String getIdFromCall(@NonNull PluginCall call) throws Exception {
        String id = call.getString("id");
        if (id == null) {
            throw CustomExceptions.ID_OR_NAME_MISSING;
        }
        return id;
    }

    private static int getImportanceFromCall(@NonNull PluginCall call) {
        return call.getInt("importance", NotificationManager.IMPORTANCE_DEFAULT);
    }

    @Nullable
    private static String getLightColorFromCall(@NonNull PluginCall call) {
        return call.getString("lightColor", null);
    }

    private static boolean getLightsFromCall(@NonNull PluginCall call) {
        return call.getBoolean("lights", false);
    }

    @NonNull
    private static String getNameFromCall(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.ID_OR_NAME_MISSING;
        }
        return name;
    }

    @Nullable
    private static String getSoundFromCall(@NonNull PluginCall call) {
        return call.getString("sound", null);
    }

    private static boolean getVibrationFromCall(@NonNull PluginCall call) {
        return call.getBoolean("vibration", false);
    }

    private static int getVisibilityFromCall(@NonNull PluginCall call) {
        return call.getInt("visibility", NotificationCompat.VISIBILITY_PUBLIC);
    }
}
