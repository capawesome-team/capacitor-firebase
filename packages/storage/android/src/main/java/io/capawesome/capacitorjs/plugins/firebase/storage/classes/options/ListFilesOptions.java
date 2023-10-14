package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

public class ListFilesOptions {

    private String path;
    private int maxResults;
    private String pageToken;

    public ListFilesOptions(String path, int maxResults, String pageToken) {
        this.path = path;
        this.maxResults = maxResults;
        this.pageToken = pageToken;
    }

    public String getPath() {
        return path;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public String getPageToken() {
        return pageToken;
    }
}
