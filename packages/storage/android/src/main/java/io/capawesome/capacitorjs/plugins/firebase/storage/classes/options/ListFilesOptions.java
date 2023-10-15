package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListFilesOptions {

    @NonNull
    private String path;

    private int maxResults;

    @Nullable
    private String pageToken;

    public ListFilesOptions(@NonNull String path, int maxResults, @Nullable String pageToken) {
        this.path = path;
        this.maxResults = maxResults;
        this.pageToken = pageToken;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public int getMaxResults() {
        return maxResults;
    }

    @Nullable
    public String getPageToken() {
        return pageToken;
    }
}
