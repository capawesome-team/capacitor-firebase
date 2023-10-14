package io.capawesome.capacitorjs.plugins.firebase.storage.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.storage.UploadTask;
import io.capawesome.capacitorjs.plugins.firebase.storage.enums.UploadFileState;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

public class UploadFileCallbackEvent implements Result {

    @Nullable
    private UploadTask.TaskSnapshot taskSnapshot;

    @NonNull
    private UploadFileState state;

    public UploadFileCallbackEvent(@Nullable UploadTask.TaskSnapshot taskSnapshot, @NonNull UploadFileState state) {
        this.taskSnapshot = taskSnapshot;
        this.state = state;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (this.taskSnapshot != null) {
            result.put("progress", (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
            result.put("bytesTransferred", taskSnapshot.getBytesTransferred());
            result.put("totalBytes", taskSnapshot.getTotalByteCount());
        }
        result.put("state", this.convertUploadFileStateToString(state));
        return result;
    }

    @Nullable
    private String convertUploadFileStateToString(UploadFileState state) {
        switch (state) {
            case CANCELED:
                return "CANCELED";
            case ERROR:
                return "ERROR";
            case PAUSED:
                return "PAUSED";
            case RUNNING:
                return "RUNNING";
            case SUCCESS:
                return "SUCCESS";
            default:
                return null;
        }
    }
}
