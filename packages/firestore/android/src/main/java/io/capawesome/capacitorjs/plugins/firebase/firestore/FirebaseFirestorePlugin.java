package io.capawesome.capacitorjs.plugins.firebase.firestore;

import androidx.annotation.Nullable;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.AddCollectionSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.AddDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.AddDocumentSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.DeleteDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.GetCollectionGroupOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.GetCollectionOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.GetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.RemoveSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.SetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.UpdateDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.Result;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.ResultCallback;

@CapacitorPlugin(name = "FirebaseFirestore")
public class FirebaseFirestorePlugin extends Plugin {

    public static final String TAG = "FirebaseFirestore";
    public static final String ERROR_REFERENCE_MISSING = "reference must be provided.";
    public static final String ERROR_CALLBACK_ID_MISSING = "callbackId must be provided.";
    public static final String ERROR_DATA_MISSING = "data must be provided.";

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
                    call.reject(exception.getMessage());
                }
            };

            implementation.addDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.setDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.getDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.updateDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.deleteDocument(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.getCollection(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.getCollectionGroup(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.enableNetwork(callback);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    call.reject(exception.getMessage());
                }
            };

            implementation.disableNetwork(callback);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addDocumentSnapshotListener(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            String callbackId = call.getCallbackId();

            AddDocumentSnapshotListenerOptions options = new AddDocumentSnapshotListenerOptions(reference, callbackId);
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

            implementation.addDocumentSnapshotListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void addCollectionSnapshotListener(PluginCall call) {
        try {
            String reference = call.getString("reference");
            if (reference == null) {
                call.reject(ERROR_REFERENCE_MISSING);
                return;
            }
            String callbackId = call.getCallbackId();

            AddCollectionSnapshotListenerOptions options = new AddCollectionSnapshotListenerOptions(reference, callbackId);
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

            implementation.addCollectionSnapshotListener(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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

            RemoveSnapshotListenerOptions options = new RemoveSnapshotListenerOptions(callbackId);
            implementation.removeSnapshotListener(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        try {
            implementation.removeAllListeners();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
