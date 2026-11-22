package io.capawesome.capacitorjs.plugins.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddCollectionGroupSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddCollectionSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.DeleteDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.EnablePersistenceOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionGroupOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCountFromServerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.RemoveSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.SetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.UpdateDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.UseEmulatorOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.WriteBatchOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.AddDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetCollectionResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetCountFromServerResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CapacitorPlugin(name = "FirebaseFirestore")
public class FirebaseFirestorePlugin extends Plugin {

    public static final String ERROR_CODE_PREFIX = "firestore";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "FirebaseFirestore";

    private FirebaseFirestoreConfig config;
    private FirebaseFirestore implementation;

    private Map<String, PluginCall> pluginCallMap = new HashMap<>();

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addCollectionGroupSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            AddCollectionGroupSnapshotListenerOptions options = new AddCollectionGroupSnapshotListenerOptions(call);

            this.pluginCallMap.put(options.getCallbackId(), call);

            NonEmptyResultCallback<GetCollectionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCollectionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.addCollectionGroupSnapshotListener(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addCollectionSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            AddCollectionSnapshotListenerOptions options = new AddCollectionSnapshotListenerOptions(call);

            this.pluginCallMap.put(options.getCallbackId(), call);

            NonEmptyResultCallback<GetCollectionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCollectionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.addCollectionSnapshotListener(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addDocument(PluginCall call) {
        try {
            AddDocumentOptions options = new AddDocumentOptions(call);
            NonEmptyResultCallback<AddDocumentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull AddDocumentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.addDocument(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addDocumentSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            AddDocumentSnapshotListenerOptions options = new AddDocumentSnapshotListenerOptions(call);

            this.pluginCallMap.put(options.getCallbackId(), call);

            NonEmptyResultCallback<GetDocumentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDocumentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.addDocumentSnapshotListener(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void clearPersistence(PluginCall call) {
        try {
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
            implementation.clearPersistence(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void deleteDocument(PluginCall call) {
        try {
            DeleteDocumentOptions options = new DeleteDocumentOptions(call);
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

            implementation.deleteDocument(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disableNetwork(PluginCall call) {
        try {
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

            implementation.disableNetwork(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disablePersistence(PluginCall call) {
        try {
            implementation.disablePersistence();
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void enableNetwork(PluginCall call) {
        try {
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

            implementation.enableNetwork(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void enablePersistence(PluginCall call) {
        try {
            EnablePersistenceOptions options = new EnablePersistenceOptions(call);

            implementation.enablePersistence(options);
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCollection(PluginCall call) {
        try {
            GetCollectionOptions options = new GetCollectionOptions(call);
            NonEmptyResultCallback<GetCollectionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCollectionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCollection(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCollectionGroup(PluginCall call) {
        try {
            GetCollectionGroupOptions options = new GetCollectionGroupOptions(call);
            NonEmptyResultCallback<GetCollectionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCollectionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCollectionGroup(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCountFromServer(PluginCall call) {
        try {
            GetCountFromServerOptions options = new GetCountFromServerOptions(call);
            NonEmptyResultCallback<GetCountFromServerResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCountFromServerResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCountFromServer(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDocument(PluginCall call) {
        try {
            GetDocumentOptions options = new GetDocumentOptions(call);
            NonEmptyResultCallback<GetDocumentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDocumentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getDocument(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void load() {
        config = getFirebaseFirestoreConfig();
        implementation = new FirebaseFirestore(this, config);
    }

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        try {
            Iterator<Map.Entry<String, PluginCall>> iterator = this.pluginCallMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, PluginCall> entry = iterator.next();
                PluginCall savedCall = entry.getValue();
                savedCall.release(this.bridge);
                iterator.remove();
            }

            implementation.removeAllListeners();
            super.removeAllListeners(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeSnapshotListener(PluginCall call) {
        try {
            RemoveSnapshotListenerOptions options = new RemoveSnapshotListenerOptions(call);

            PluginCall savedCall = this.pluginCallMap.remove(options.getCallbackId());
            if (savedCall != null) {
                savedCall.release(this.bridge);
            }

            implementation.removeSnapshotListener(options);
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setDocument(PluginCall call) {
        try {
            SetDocumentOptions options = new SetDocumentOptions(call);
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

            implementation.setDocument(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void terminate(PluginCall call) {
        try {
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

            implementation.terminate(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateDocument(PluginCall call) {
        try {
            UpdateDocumentOptions options = new UpdateDocumentOptions(call);
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

            implementation.updateDocument(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void useEmulator(PluginCall call) {
        try {
            UseEmulatorOptions options = new UseEmulatorOptions(call);

            implementation.useEmulator(options);
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void writeBatch(PluginCall call) {
        try {
            WriteBatchOptions options = new WriteBatchOptions(call);
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

            implementation.writeBatch(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private FirebaseFirestoreConfig getFirebaseFirestoreConfig() {
        FirebaseFirestoreConfig config = new FirebaseFirestoreConfig();

        String databaseId = getConfig().getString("databaseId");
        config.setDatabaseId(databaseId);

        return config;
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException
            ? ((CustomException) exception).getCode()
            : FirebaseFirestoreHelper.createErrorCode(exception);
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
