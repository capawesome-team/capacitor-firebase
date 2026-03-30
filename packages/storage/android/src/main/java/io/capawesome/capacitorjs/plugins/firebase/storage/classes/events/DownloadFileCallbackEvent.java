package io.capawesome.capacitorjs.plugins.firebase.storage.classes.events;

import com.getcapacitor.JSObject;
import com.google.firebase.storage.FileDownloadTask;
import io.capawesome.capacitorjs.plugins.firebase.storage.enums.DownloadFileState;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

public class DownloadFileCallbackEvent implements Result {

    private FileDownloadTask.TaskSnapshot taskSnapshot;
    private DownloadFileState state;

    public DownloadFileCallbackEvent(FileDownloadTask.TaskSnapshot taskSnapshot, DownloadFileState state) {
        this.taskSnapshot = taskSnapshot;
        this.state = state;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("progress", (double) taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
        result.put("bytesTransferred", taskSnapshot.getBytesTransferred());
        result.put("totalBytes", taskSnapshot.getTotalByteCount());
        result.put("completed", state == DownloadFileState.SUCCESS);
        return result;
    }
}
