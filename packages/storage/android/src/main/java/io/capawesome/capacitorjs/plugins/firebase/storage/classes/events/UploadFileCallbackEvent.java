package io.capawesome.capacitorjs.plugins.firebase.storage.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.storage.UploadTask;
import io.capawesome.capacitorjs.plugins.firebase.storage.enums.UploadFileState;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

public class UploadFileCallbackEvent implements Result {

    private UploadTask.TaskSnapshot taskSnapshot;

    private UploadFileState state;

    public UploadFileCallbackEvent(UploadTask.TaskSnapshot taskSnapshot, UploadFileState state) {
        this.taskSnapshot = taskSnapshot;
        this.state = state;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("progress", (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
        result.put("bytesTransferred", taskSnapshot.getBytesTransferred());
        result.put("totalBytes", taskSnapshot.getTotalByteCount());
        result.put("completed", state == UploadFileState.SUCCESS);
        return result;
    }
}
