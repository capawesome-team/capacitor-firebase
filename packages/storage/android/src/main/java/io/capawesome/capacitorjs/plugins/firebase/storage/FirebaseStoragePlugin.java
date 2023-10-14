package io.capawesome.capacitorjs.plugins.firebase.storage;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.DeleteFileOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.GetDownloadUrlOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.GetMetadataOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.ListFilesOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.UpdateMetadataOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.UploadFileOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

@CapacitorPlugin(name = "FirebaseStorage")
public class FirebaseStoragePlugin extends Plugin {

    public static final String TAG = "FirebaseStorage";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_URI_MISSING = "uri must be provided.";

    private FirebaseStorage implementation;

    public void load() {
        implementation = new FirebaseStorage(this);
    }

    @PluginMethod
    public void deleteFile(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }

            DeleteFileOptions options = new DeleteFileOptions(path);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.deleteFile(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getDownloadUrl(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }

            GetDownloadUrlOptions options = new GetDownloadUrlOptions(path);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getDownloadUrl(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getMetadata(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }

            GetMetadataOptions options = new GetMetadataOptions(path);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getMetadata(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void listFiles(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            int maxResults = call.getInt("maxResults", 1000);
            String pageToken = call.getString("pageToken");

            ListFilesOptions options = new ListFilesOptions(path, maxResults, pageToken);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.listFiles(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void updateMetadata(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            JSObject metadata = call.getObject("metadata");

            UpdateMetadataOptions options = new UpdateMetadataOptions(path, metadata);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.updateMetadata(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void uploadFile(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            String uri = call.getString("uri");
            if (uri == null) {
                call.reject(ERROR_URI_MISSING);
                return;
            }
            String callbackId = call.getCallbackId();

            UploadFileOptions options = new UploadFileOptions(path, uri, callbackId);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.uploadFile(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
