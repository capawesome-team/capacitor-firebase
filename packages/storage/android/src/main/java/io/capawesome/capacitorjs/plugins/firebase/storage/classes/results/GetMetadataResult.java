package io.capawesome.capacitorjs.plugins.firebase.storage.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageMetadata;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;
import java.util.Set;

public class GetMetadataResult implements Result {

    private StorageMetadata referenceResult;

    public GetMetadataResult(StorageMetadata storageMetadata) {
        this.referenceResult = storageMetadata;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bucket", referenceResult.getBucket());
        result.put("createdAt", referenceResult.getCreationTimeMillis());
        result.put("generation", referenceResult.getGeneration());
        result.put("md5Hash", referenceResult.getMd5Hash());
        result.put("metadataGeneration", referenceResult.getMetadataGeneration());
        result.put("name", referenceResult.getName());
        result.put("path", referenceResult.getPath());
        result.put("size", referenceResult.getSizeBytes());
        result.put("updatedAt", referenceResult.getUpdatedTimeMillis());

        Set<String> customMetadataKeys = referenceResult.getCustomMetadataKeys();
        if (!customMetadataKeys.isEmpty()) {
            JSObject customMetadata = new JSObject();
            for (String key : customMetadataKeys) {
                customMetadata.put(key, referenceResult.getCustomMetadata(key));
            }
            result.put("customMetadata", customMetadata);
        }

        return result;
    }
}
