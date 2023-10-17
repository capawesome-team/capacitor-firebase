package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageMetadata;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMetadataOptions {

    private String path;
    private StorageMetadata metadata;

    public UpdateMetadataOptions(String path, JSObject metadata) throws JSONException {
        this.path = path;
        this.metadata = buildStorageMetadata(metadata);
    }

    public String getPath() {
        return path;
    }

    public StorageMetadata getMetadata() {
        return metadata;
    }

    private StorageMetadata buildStorageMetadata(JSObject metadata) throws JSONException {
        String cacheControl = metadata.getString("cacheControl");
        String contentDisposition = metadata.getString("contentDisposition");
        String contentEncoding = metadata.getString("contentEncoding");
        String contentLanguage = metadata.getString("contentLanguage");
        String contentType = metadata.getString("contentType");
        JSONObject customMetadata = metadata.getJSONObject("customMetadata");

        StorageMetadata.Builder builder = new StorageMetadata.Builder();
        if (cacheControl != null) {
            builder = builder.setCacheControl(cacheControl);
        }
        if (contentDisposition != null) {
            builder = builder.setContentDisposition(contentDisposition);
        }
        if (contentEncoding != null) {
            builder = builder.setContentEncoding(contentEncoding);
        }
        if (contentLanguage != null) {
            builder = builder.setContentLanguage(contentLanguage);
        }
        if (contentType != null) {
            builder = builder.setContentType(contentType);
        }
        if (customMetadata != null) {
            Iterator<String> keys = customMetadata.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = customMetadata.get(key);
                if (value instanceof String) {
                    builder = builder.setCustomMetadata(key, (String) value);
                }
            }
        }

        return builder.build();
    }
}
