package app.capgo.capacitor.firebase.storage.classes.options;

public class GetDownloadUrlOptions {

    private String path;

    public GetDownloadUrlOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
