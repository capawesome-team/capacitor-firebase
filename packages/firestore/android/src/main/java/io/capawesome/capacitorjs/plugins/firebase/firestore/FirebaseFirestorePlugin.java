package io.capawesome.capacitorjs.plugins.firebase.firestore;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseFirestore")
public class FirebaseFirestorePlugin extends Plugin {

    public static final String TAG = "FirebaseFirestore";
    public static final String ERROR_REFERENCE_MISSING = "reference must be provided.";
    public static final String ERROR_CALLBACK_ID_MISSING = "callbackId must be provided.";
    public static final String ERROR_ERROR_DATA_MISSING = "data must be provided.";

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
            ResultCallback callback = new ResultCallback() {
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

            SetDocumentOptions options = new SetDocumentOptions(reference, data);
            ResultCallback callback = new ResultCallback() {
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
            ResultCallback callback = new ResultCallback() {
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
            ResultCallback callback = new ResultCallback() {
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
            ResultCallback callback = new ResultCallback() {
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
            JSObject compositeFilter = call.getObject("compositeFilter");
            JSArray queryConstraints = call.getArray("queryConstraints");

            GetCollectionOptions options = new GetCollectionOptions(reference, compositeFilter, queryConstraints);
            ResultCallback callback = new ResultCallback() {
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
            ResultCallback callback = new ResultCallback() {
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
            implementation.enableNetwork();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void disableNetwork(PluginCall call) {
        try {
            implementation.disableNetwork();
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

            AddDocumentSnapshotListenerOptions options = new AddDocumentSnapshotListenerOptions(reference);
            ResultCallback callback = new ResultCallback() {
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

            AddCollectionSnapshotListenerOptions options = new AddCollectionSnapshotListenerOptions(reference);
            ResultCallback callback = new ResultCallback() {
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

            RemoveSnapshotListenerOptions options = new RemoveSnapshotListenerOptions(listenerId);
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
