package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

public class DeleteFileOptions {

    private String path;

    public DeleteFileOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
