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
        result.put("progress", getProgress());
        result.put("bytesTransferred", taskSnapshot.getBytesTransferred());
        result.put("totalBytes", taskSnapshot.getTotalByteCount());
        result.put("completed", state == UploadFileState.SUCCESS);
        return result;
    }

    private double getProgress() {
        long totalByteCount = taskSnapshot.getTotalByteCount();
        if (totalByteCount == 0) {
            // An empty file has nothing to transfer, so it is done from the start. iOS reports 1 for it too.
            return 1;
        }
        return (double) taskSnapshot.getBytesTransferred() / totalByteCount;
    }
}
