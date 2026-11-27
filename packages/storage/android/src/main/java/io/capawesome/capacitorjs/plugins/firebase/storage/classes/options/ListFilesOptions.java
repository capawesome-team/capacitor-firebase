package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomExceptions;

public class ListFilesOptions {

    private final int maxResults;

    @Nullable
    private final String pageToken;

    @NonNull
    private final String path;

    public ListFilesOptions(@NonNull PluginCall call) throws Exception {
        this.path = ListFilesOptions.getPathFromCall(call);
        this.maxResults = ListFilesOptions.getMaxResultsFromCall(call);
        this.pageToken = ListFilesOptions.getPageTokenFromCall(call);
    }

    public int getMaxResults() {
        return maxResults;
    }

    @Nullable
    public String getPageToken() {
        return pageToken;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    private static int getMaxResultsFromCall(@NonNull PluginCall call) {
        return call.getInt("maxResults", 1000);
    }

    @Nullable
    private static String getPageTokenFromCall(@NonNull PluginCall call) {
        return call.getString("pageToken");
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return path;
    }
}
