package io.capawesome.capacitorjs.plugins.firebase.storage.classes.results;

import com.getcapacitor.JSObject;
import com.google.firebase.storage.ListResult;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

public class ListFilesResult implements Result {

    private ListResult listResult;

    public ListFilesResult(ListResult listResult) {
        this.listResult = listResult;
    }

    public JSObject toJSObject() {
        JSObject itemsResult = new JSObject();
        for (int i = 0; i < listResult.getItems().size(); i++) {
            JSObject itemResult = new JSObject();
            itemResult.put("bucket", listResult.getItems().get(i).getBucket());
            itemResult.put("path", listResult.getItems().get(i).getPath());
            itemResult.put("name", listResult.getItems().get(i).getName());
        }

        JSObject result = new JSObject();
        result.put("items", itemsResult);
        result.put("nextPageToken", listResult.getPageToken());
        return result;
    }
}
