package io.capawesome.capacitorjs.plugins.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddCollectionGroupSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddCollectionSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.ClearPersistenceOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.DeleteDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.DisableNetworkOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.DisablePersistenceOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.EnableNetworkOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.EnablePersistenceOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionGroupOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCountFromServerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.RemoveSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.SetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.TerminateOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.UpdateDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.UseEmulatorOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.WriteBatchOperation;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.WriteBatchOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.AddDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetCollectionResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetCountFromServerResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import java.util.HashMap;
import java.util.Map;

public class FirebaseFirestore {

    private FirebaseFirestoreConfig config;
    private Map<String, ListenerRegistration> listenerRegistrationMap = new HashMap<>();
    private FirebaseFirestorePlugin plugin;

    public FirebaseFirestore(FirebaseFirestorePlugin plugin, FirebaseFirestoreConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void addCollectionGroupSnapshotListener(
        @NonNull AddCollectionGroupSnapshotListenerOptions options,
        @NonNull NonEmptyResultCallback<GetCollectionResult> callback
    ) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(
            options.getCompositeFilter(),
            firestore
        );
        QueryNonFilterConstraint[] queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(
            options.getQueryConstraints()
        );
        String callbackId = options.getCallbackId();
        DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(
            options.getServerTimestampBehavior()
        );

        Query query = firestore.collectionGroup(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, firestore);
            }
        }

        ListenerRegistration listenerRegistration = query.addSnapshotListener(
            options.isIncludeMetadataChanges() ? MetadataChanges.INCLUDE : MetadataChanges.EXCLUDE,
            (querySnapshot, exception) -> {
                if (exception != null) {
                    callback.error(exception);
                } else {
                    GetCollectionResult result = new GetCollectionResult(querySnapshot, serverTimestampBehavior);
                    callback.success(result);
                }
            }
        );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void addCollectionSnapshotListener(
        @NonNull AddCollectionSnapshotListenerOptions options,
        @NonNull NonEmptyResultCallback<GetCollectionResult> callback
    ) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(
            options.getCompositeFilter(),
            firestore
        );
        QueryNonFilterConstraint[] queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(
            options.getQueryConstraints()
        );
        String callbackId = options.getCallbackId();
        DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(
            options.getServerTimestampBehavior()
        );

        Query query = firestore.collection(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, firestore);
            }
        }

        ListenerRegistration listenerRegistration = query.addSnapshotListener(
            options.isIncludeMetadataChanges() ? MetadataChanges.INCLUDE : MetadataChanges.EXCLUDE,
            (querySnapshot, exception) -> {
                if (exception != null) {
                    callback.error(exception);
                } else {
                    GetCollectionResult result = new GetCollectionResult(querySnapshot, serverTimestampBehavior);
                    callback.success(result);
                }
            }
        );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void addDocument(@NonNull AddDocumentOptions options, @NonNull NonEmptyResultCallback<AddDocumentResult> callback)
        throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        Map<String, Object> data = FirebaseFirestoreHelper.createHashMapFromJSONObject(options.getData(), firestore);

        firestore
            .collection(reference)
            .add(data)
            .addOnSuccessListener(documentReference -> {
                AddDocumentResult result = new AddDocumentResult(documentReference);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void addDocumentSnapshotListener(
        @NonNull AddDocumentSnapshotListenerOptions options,
        @NonNull NonEmptyResultCallback<GetDocumentResult> callback
    ) {
        String reference = options.getReference();
        String callbackId = options.getCallbackId();
        DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(
            options.getServerTimestampBehavior()
        );

        ListenerRegistration listenerRegistration = getFirebaseFirestoreInstance(options.getAppName())
            .document(reference)
            .addSnapshotListener(
                options.isIncludeMetadataChanges() ? MetadataChanges.INCLUDE : MetadataChanges.EXCLUDE,
                (documentSnapshot, exception) -> {
                    if (exception != null) {
                        callback.error(exception);
                    } else {
                        GetDocumentResult result = new GetDocumentResult(documentSnapshot, serverTimestampBehavior);
                        callback.success(result);
                    }
                }
            );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void clearPersistence(@NonNull ClearPersistenceOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseFirestoreInstance(options.getAppName())
            .clearPersistence()
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void deleteDocument(@NonNull DeleteDocumentOptions options, @NonNull EmptyResultCallback callback) {
        String reference = options.getReference();

        getFirebaseFirestoreInstance(options.getAppName())
            .document(reference)
            .delete()
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void disableNetwork(@NonNull DisableNetworkOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseFirestoreInstance(options.getAppName())
            .disableNetwork()
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void disablePersistence(@NonNull DisablePersistenceOptions options, @NonNull EmptyResultCallback callback) {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder(firestore.getFirestoreSettings())
            .setPersistenceEnabled(false)
            .build();
        firestore.setFirestoreSettings(settings);
        callback.success();
    }

    public void enableNetwork(@NonNull EnableNetworkOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseFirestoreInstance(options.getAppName())
            .enableNetwork()
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void enablePersistence(@NonNull EnablePersistenceOptions options, @NonNull EmptyResultCallback callback) {
        Long cacheSizeBytes = options.getCacheSizeBytes();

        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        FirebaseFirestoreSettings.Builder builder = new FirebaseFirestoreSettings.Builder(
            firestore.getFirestoreSettings()
        ).setPersistenceEnabled(true);
        if (cacheSizeBytes != null) {
            builder.setCacheSizeBytes(cacheSizeBytes);
        }
        firestore.setFirestoreSettings(builder.build());
        callback.success();
    }

    public void getCollection(@NonNull GetCollectionOptions options, @NonNull NonEmptyResultCallback<GetCollectionResult> callback)
        throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(
            options.getCompositeFilter(),
            firestore
        );
        QueryNonFilterConstraint[] queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(
            options.getQueryConstraints()
        );

        Query query = firestore.collection(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            if (filter != null) {
                query = query.where(filter);
            }
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, firestore);
            }
        }
        query
            .get()
            .addOnSuccessListener(querySnapshot -> {
                GetCollectionResult result = new GetCollectionResult(querySnapshot);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getCollectionGroup(
        @NonNull GetCollectionGroupOptions options,
        @NonNull NonEmptyResultCallback<GetCollectionResult> callback
    ) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(
            options.getCompositeFilter(),
            firestore
        );
        QueryNonFilterConstraint[] queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(
            options.getQueryConstraints()
        );

        Query query = firestore.collectionGroup(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, firestore);
            }
        }
        query
            .get()
            .addOnSuccessListener(querySnapshot -> {
                GetCollectionResult result = new GetCollectionResult(querySnapshot);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getCountFromServer(
        @NonNull GetCountFromServerOptions options,
        @NonNull NonEmptyResultCallback<GetCountFromServerResult> callback
    ) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(
            options.getCompositeFilter(),
            firestore
        );
        QueryNonFilterConstraint[] queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(
            options.getQueryConstraints()
        );

        Query query = firestore.collection(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            if (filter != null) {
                query = query.where(filter);
            }
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, firestore);
            }
        }
        AggregateQuery countQuery = query.count();

        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
                GetCountFromServerResult result = new GetCountFromServerResult(snapshot.getCount());
                callback.success(result);
            } else {
                callback.error(task.getException());
            }
        });
    }

    public void getDocument(@NonNull GetDocumentOptions options, @NonNull NonEmptyResultCallback<GetDocumentResult> callback) {
        String reference = options.getReference();

        getFirebaseFirestoreInstance(options.getAppName())
            .document(reference)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                GetDocumentResult result = new GetDocumentResult(documentSnapshot);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void removeAllListeners(@NonNull EmptyResultCallback callback) {
        for (ListenerRegistration listenerRegistration : this.listenerRegistrationMap.values()) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.clear();
        callback.success();
    }

    public void removeSnapshotListener(@NonNull RemoveSnapshotListenerOptions options, @NonNull EmptyResultCallback callback) {
        String callbackId = options.getCallbackId();

        ListenerRegistration listenerRegistration = this.listenerRegistrationMap.get(callbackId);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.remove(callbackId);
        callback.success();
    }

    public void setDocument(@NonNull SetDocumentOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        Map<String, Object> data = FirebaseFirestoreHelper.createHashMapFromJSONObject(options.getData(), firestore);
        boolean merge = options.getMerge();

        DocumentReference documentReference = firestore.document(reference);
        Task<Void> task;
        if (merge) {
            task = documentReference.set(data, SetOptions.merge());
        } else {
            task = documentReference.set(data);
        }
        task.addOnSuccessListener(unused -> callback.success()).addOnFailureListener(exception -> callback.error(exception));
    }

    public void terminate(@NonNull TerminateOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseFirestoreInstance(options.getAppName())
            .terminate()
            .addOnSuccessListener(unused -> {
                callback.success();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void updateDocument(@NonNull UpdateDocumentOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        String reference = options.getReference();
        Map<String, Object> data = FirebaseFirestoreHelper.createHashMapFromJSONObject(options.getData(), firestore);

        firestore
            .document(reference)
            .update(data)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void useEmulator(@NonNull UseEmulatorOptions options, @NonNull EmptyResultCallback callback) {
        String host = options.getHost();
        int port = options.getPort();

        getFirebaseFirestoreInstance(options.getAppName()).useEmulator(host, port);
        callback.success();
    }

    public void writeBatch(@NonNull WriteBatchOptions options, @NonNull EmptyResultCallback callback) throws Exception {
        com.google.firebase.firestore.FirebaseFirestore firestore = getFirebaseFirestoreInstance(options.getAppName());
        WriteBatchOperation[] operations = options.getOperations();

        WriteBatch batch = firestore.batch();
        for (WriteBatchOperation operation : operations) {
            String type = operation.getType();
            String reference = operation.getReference();
            Map<String, Object> data = FirebaseFirestoreHelper.createHashMapFromJSONObject(operation.getData(), firestore);

            DocumentReference documentReference = firestore.document(reference);
            switch (type) {
                case "set":
                    if (operation.getOptions() != null && operation.getOptions().isMerge()) {
                        batch.set(documentReference, data, SetOptions.merge());
                    } else {
                        batch.set(documentReference, data);
                    }
                    break;
                case "update":
                    batch.update(documentReference, data);
                    break;
                case "delete":
                    batch.delete(documentReference);
                    break;
            }
        }
        batch
            .commit()
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    @NonNull
    private com.google.firebase.firestore.FirebaseFirestore getFirebaseFirestoreInstance(@Nullable String appName) {
        if (appName != null) {
            return com.google.firebase.firestore.FirebaseFirestore.getInstance(FirebaseApp.getInstance(appName));
        }
        String databaseId = config.getDatabaseId();
        if (databaseId != null) {
            return com.google.firebase.firestore.FirebaseFirestore.getInstance(databaseId);
        }
        return com.google.firebase.firestore.FirebaseFirestore.getInstance();
    }
}
