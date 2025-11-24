package app.capgo.capacitor.firebase.firestore;

import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import app.capgo.capacitor.firebase.firestore.classes.options.AddCollectionGroupSnapshotListenerOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.AddCollectionSnapshotListenerOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.AddDocumentOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.AddDocumentSnapshotListenerOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.DeleteDocumentOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.GetCollectionGroupOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.GetCollectionOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.GetCountFromServerOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.GetDocumentOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.RemoveSnapshotListenerOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.SetDocumentOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.UpdateDocumentOptions;
import app.capgo.capacitor.firebase.firestore.classes.options.WriteBatchOptions;
import app.capgo.capacitor.firebase.firestore.interfaces.EmptyResultCallback;
import app.capgo.capacitor.firebase.firestore.interfaces.NonEmptyResultCallback;
import app.capgo.capacitor.firebase.firestore.interfaces.Result;
import app.capgo.capacitor.firebase.firestore.interfaces.ResultCallback;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CapacitorPlugin(name = "FirebaseFirestore")
public class FirebaseFirestorePlugin extends Plugin {

    public static final String TAG = "FirebaseFirestore";
    public static final String ERROR_CODE_PREFIX = "firestore";
    public static final String ERROR_REFERENCE_MISSING = "reference must be provided.";
    public static final String ERROR_HOST_MISSING = "host must be provided.";
    public static final String ERROR_CALLBACK_ID_MISSING = "callbackId must be provided.";
    public static final String ERROR_DATA_MISSING = "data must be provided.";
    public static final String ERROR_OPERATIONS_MISSING = "operations must be provided.";

    private Map<String, PluginCall> pluginCallMap = new HashMap<>();

    private FirebaseFirestore implementation;

    public void load() {
        implementation = new FirebaseFirestore(this);
    }

    @PluginMethod
    public void addDocument(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject data = call.getObject("data");
            if (data == null) {
                call.reject(ERROR_DATA_MISSING);
                return;
            }

            AddDocumentOptions options = new AddDocumentOptions(reference, data);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.addDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void setDocument(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject data = call.getObject("data");
            if (data == null) {
                call.reject(ERROR_DATA_MISSING);
                return;
            }
            boolean merge = call.getBoolean("merge", false);

            SetDocumentOptions options = new SetDocumentOptions(reference, data, merge);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.setDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void getDocument(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }

            GetDocumentOptions options = new GetDocumentOptions(reference);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.getDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void updateDocument(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject data = call.getObject("data");
            if (data == null) {
                call.reject(ERROR_DATA_MISSING);
                return;
            }

            UpdateDocumentOptions options = new UpdateDocumentOptions(reference, data);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.updateDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void deleteDocument(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }

            DeleteDocumentOptions options = new DeleteDocumentOptions(reference);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.deleteDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void writeBatch(PluginCall call) {
        try {
            JSArray operations = call.getArray("operations");
            if (operations == null) {
                call.reject(ERROR_OPERATIONS_MISSING);
                return;
            }

            WriteBatchOptions options = new WriteBatchOptions(operations);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.writeBatch(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void getCollection(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject compositeFilter = call.getObject("compositeFilter", null);
            JSArray queryConstraints = call.getArray("queryConstraints", null);

            GetCollectionOptions options = new GetCollectionOptions(reference, compositeFilter, queryConstraints);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.getCollection(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void getCollectionGroup(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject compositeFilter = call.getObject("compositeFilter");
            JSArray queryConstraints = call.getArray("queryConstraints");

            GetCollectionGroupOptions options = new GetCollectionGroupOptions(reference, compositeFilter, queryConstraints);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.getCollectionGroup(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void clearPersistence(PluginCall call) {
        try {
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };
            implementation.clearPersistence(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void enableNetwork(PluginCall call) {
        try {
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.enableNetwork(callback);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void disableNetwork(PluginCall call) {
        try {
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.disableNetwork(callback);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void useEmulator(PluginCall call) {
        try {
            String host = call.getString("host");
            if (host == null) {
                call.reject(ERROR_HOST_MISSING);
                return;
            }
            int port = call.getInt("port", 8080);

            implementation.useEmulator(host, port);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void getCountFromServer(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }

            GetCountFromServerOptions options = new GetCountFromServerOptions(reference);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.getCountFromServer(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addDocumentSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            Boolean includeMetadataChanges = call.getBoolean("includeMetadataChanges");
            String callbackId = call.getCallbackId();

            this.pluginCallMap.put(callbackId, call);

            AddDocumentSnapshotListenerOptions options = new AddDocumentSnapshotListenerOptions(
                reference,
                includeMetadataChanges,
                callbackId
            );
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.addDocumentSnapshotListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addCollectionSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject compositeFilter = call.getObject("compositeFilter");
            JSArray queryConstraints = call.getArray("queryConstraints");
            Boolean includeMetadataChanges = call.getBoolean("includeMetadataChanges");
            String callbackId = call.getCallbackId();

            this.pluginCallMap.put(callbackId, call);

            AddCollectionSnapshotListenerOptions options = new AddCollectionSnapshotListenerOptions(
                reference,
                compositeFilter,
                queryConstraints,
                includeMetadataChanges,
                callbackId
            );
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.addCollectionSnapshotListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addCollectionGroupSnapshotListener(PluginCall call) {
        try {
            call.setKeepAlive(true);

            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            JSObject compositeFilter = call.getObject("compositeFilter");
            JSArray queryConstraints = call.getArray("queryConstraints");
            Boolean includeMetadataChanges = call.getBoolean("includeMetadataChanges");
            String callbackId = call.getCallbackId();

            this.pluginCallMap.put(callbackId, call);

            AddCollectionGroupSnapshotListenerOptions options = new AddCollectionGroupSnapshotListenerOptions(
                reference,
                compositeFilter,
                queryConstraints,
                includeMetadataChanges,
                callbackId
            );
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
                }
            };

            implementation.addCollectionGroupSnapshotListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }

    @PluginMethod
    public void removeSnapshotListener(PluginCall call) {
        try {
            String callbackId = call.getString("callbackId");
            if (callbackId == null) {
                call.reject(ERROR_CALLBACK_ID_MISSING);
                return;
            }

            PluginCall savedCall = this.pluginCallMap.remove(callbackId);
            savedCall.release(this.bridge);

            RemoveSnapshotListenerOptions options = new RemoveSnapshotListenerOptions(callbackId);
            implementation.removeSnapshotListener(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
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
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage(), FirebaseFirestoreHelper.createErrorCode(exception));
        }
    }
}
