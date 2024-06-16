package io.capawesome.capacitorjs.plugins.firebase.storage;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageMetadata;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseStorageHelper {

    @Nullable
    public static StorageMetadata buildStorageMetadata(@Nullable JSObject metadata) throws JSONException {
        if (metadata == null) {
            return null;
        }
        String cacheControl = metadata.getString("cacheControl");
        String contentDisposition = metadata.getString("contentDisposition");
        String contentEncoding = metadata.getString("contentEncoding");
        String contentLanguage = metadata.getString("contentLanguage");
        String contentType = metadata.getString("contentType");
        JSONObject customMetadata = metadata.getJSObject("customMetadata");

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
