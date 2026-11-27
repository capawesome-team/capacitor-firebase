package io.capawesome.capacitorjs.plugins.firebase.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.events.DownloadFileCallbackEvent;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.events.UploadFileCallbackEvent;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.DeleteFileOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.DownloadFileOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.GetDownloadUrlOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.GetMetadataOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.ListFilesOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.UpdateMetadataOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.UploadFileOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.options.UseEmulatorOptions;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.results.GetDownloadUrlResult;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.results.GetMetadataResult;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.results.ListFilesResult;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.NonEmptyEventCallback;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.storage.interfaces.Result;

@CapacitorPlugin(name = "FirebaseStorage")
public class FirebaseStoragePlugin extends Plugin {

    public static final String TAG = "FirebaseStorage";

    private FirebaseStorage implementation;

    @PluginMethod
    public void deleteFile(PluginCall call) {
        try {
            DeleteFileOptions options = new DeleteFileOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.deleteFile(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void downloadFile(PluginCall call) {
        try {
            call.setKeepAlive(true);

            DownloadFileOptions options = new DownloadFileOptions(call);
            NonEmptyEventCallback<DownloadFileCallbackEvent> callback = new NonEmptyEventCallback<>() {
                @Override
                public void success(@NonNull DownloadFileCallbackEvent result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }

                @Override
                public void release() {
                    call.release(bridge);
                }
            };

            implementation.downloadFile(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDownloadUrl(PluginCall call) {
        try {
            GetDownloadUrlOptions options = new GetDownloadUrlOptions(call);
            NonEmptyResultCallback<GetDownloadUrlResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDownloadUrlResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getDownloadUrl(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getMetadata(PluginCall call) {
        try {
            GetMetadataOptions options = new GetMetadataOptions(call);
            NonEmptyResultCallback<GetMetadataResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetMetadataResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getMetadata(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void listFiles(PluginCall call) {
        try {
            ListFilesOptions options = new ListFilesOptions(call);
            NonEmptyResultCallback<ListFilesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ListFilesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.listFiles(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void load() {
        implementation = new FirebaseStorage(this);
    }

    @PluginMethod
    public void updateMetadata(PluginCall call) {
        try {
            UpdateMetadataOptions options = new UpdateMetadataOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.updateMetadata(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void uploadFile(PluginCall call) {
        try {
            call.setKeepAlive(true);

            UploadFileOptions options = new UploadFileOptions(call);
            NonEmptyEventCallback<UploadFileCallbackEvent> callback = new NonEmptyEventCallback<>() {
                @Override
                public void success(@NonNull UploadFileCallbackEvent result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }

                @Override
                public void release() {
                    call.release(bridge);
                }
            };

            implementation.uploadFile(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void useEmulator(PluginCall call) {
        try {
            UseEmulatorOptions options = new UseEmulatorOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.useEmulator(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException
            ? ((CustomException) exception).getCode()
            : FirebaseStorageHelper.createErrorCode(exception);
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
