package app.capgo.capacitor.firebase.storage.classes.options;

import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageMetadata;
import app.capgo.capacitor.firebase.storage.FirebaseStorageHelper;
import org.json.JSONException;

public class UpdateMetadataOptions {

    private String path;
    private StorageMetadata metadata;

    public UpdateMetadataOptions(String path, JSObject metadata) throws JSONException {
        this.path = path;
        this.metadata = FirebaseStorageHelper.buildStorageMetadata(metadata);
    }

    public String getPath() {
        return path;
    }

    public StorageMetadata getMetadata() {
        return metadata;
    }
}
