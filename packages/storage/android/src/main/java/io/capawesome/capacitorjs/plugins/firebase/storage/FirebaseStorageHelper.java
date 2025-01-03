package io.capawesome.capacitorjs.plugins.firebase.storage;

import static com.google.firebase.storage.StorageException.ERROR_BUCKET_NOT_FOUND;
import static com.google.firebase.storage.StorageException.ERROR_CANCELED;
import static com.google.firebase.storage.StorageException.ERROR_INVALID_CHECKSUM;
import static com.google.firebase.storage.StorageException.ERROR_NOT_AUTHENTICATED;
import static com.google.firebase.storage.StorageException.ERROR_NOT_AUTHORIZED;
import static com.google.firebase.storage.StorageException.ERROR_OBJECT_NOT_FOUND;
import static com.google.firebase.storage.StorageException.ERROR_PROJECT_NOT_FOUND;
import static com.google.firebase.storage.StorageException.ERROR_QUOTA_EXCEEDED;
import static com.google.firebase.storage.StorageException.ERROR_RETRY_LIMIT_EXCEEDED;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageException;
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

    @Nullable
    public static String createErrorCode(@Nullable Exception exception) {
        if (exception == null) {
            return null;
        } else if (exception instanceof StorageException) {
            return convertErrorCodeToString(((StorageException) exception).getErrorCode());
        }
        return null;
    }

    private static String convertErrorCodeToString(int code) {
        return switch (code) {
            case ERROR_BUCKET_NOT_FOUND -> "storage/bucket-not-found";
            case ERROR_CANCELED -> "storage/canceled";
            case ERROR_INVALID_CHECKSUM -> "storage/invalid-checksum";
            case ERROR_NOT_AUTHENTICATED -> "storage/unauthenticated";
            case ERROR_NOT_AUTHORIZED -> "storage/unauthorized";
            case ERROR_OBJECT_NOT_FOUND -> "storage/object-not-found";
            case ERROR_PROJECT_NOT_FOUND -> "storage/project-not-found";
            case ERROR_QUOTA_EXCEEDED -> "storage/quota-exceeded";
            case ERROR_RETRY_LIMIT_EXCEEDED -> "storage/retry-limit-exceeded";
            default -> "storage/unknown";
        };
    }

    private static String snakeToKebabCase(String snakeCase) {
        return snakeCase.replaceAll("_+", "-").toLowerCase();
    }
}
