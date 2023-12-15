package io.capawesome.capacitorjs.plugins.firebase.firestore;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddCollectionSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.AddDocumentSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.DeleteDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionGroupOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetCollectionOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.GetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.RemoveSnapshotListenerOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.SetDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options.UpdateDocumentOptions;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.AddDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetCollectionResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.results.GetDocumentResult;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import java.util.HashMap;
import java.util.Map;

public class FirebaseFirestore {

    private FirebaseFirestorePlugin plugin;
    private com.google.firebase.firestore.FirebaseFirestore firestoreInstance;
    private Map<String, ListenerRegistration> listenerRegistrationMap = new HashMap<>();

    public FirebaseFirestore(FirebaseFirestorePlugin plugin) {
        this.plugin = plugin;
        firestoreInstance = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    }

    public void addDocument(@NonNull AddDocumentOptions options, @NonNull NonEmptyResultCallback callback) {
        String reference = options.getReference();
        Map<String, Object> data = options.getData();

        this.firestoreInstance.collection(reference)
            .add(data)
            .addOnSuccessListener(
                documentReference -> {
                    AddDocumentResult result = new AddDocumentResult(documentReference);
                    callback.success(result);
                }
            )
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void setDocument(@NonNull SetDocumentOptions options, @NonNull EmptyResultCallback callback) {
        String reference = options.getReference();
        Map<String, Object> data = options.getData();
        boolean merge = options.getMerge();

        DocumentReference documentReference = this.firestoreInstance.document(reference);
        Task<Void> task;
        if (merge) {
            task = documentReference.set(data, SetOptions.merge());
        } else {
            task = documentReference.set(data);
        }
        task.addOnSuccessListener(unused -> callback.success()).addOnFailureListener(exception -> callback.error(exception));
    }

    public void getDocument(@NonNull GetDocumentOptions options, @NonNull NonEmptyResultCallback callback) {
        String reference = options.getReference();

        this.firestoreInstance.document(reference)
            .get()
            .addOnSuccessListener(
                documentSnapshot -> {
                    GetDocumentResult result = new GetDocumentResult(documentSnapshot);
                    callback.success(result);
                }
            )
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void updateDocument(@NonNull UpdateDocumentOptions options, @NonNull EmptyResultCallback callback) {
        String reference = options.getReference();
        Map<String, Object> data = options.getData();

        this.firestoreInstance.document(reference)
            .update(data)
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void deleteDocument(@NonNull DeleteDocumentOptions options, @NonNull EmptyResultCallback callback) {
        String reference = options.getReference();

        this.firestoreInstance.document(reference)
            .delete()
            .addOnSuccessListener(unused -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getCollection(@NonNull GetCollectionOptions options, @NonNull NonEmptyResultCallback callback) throws Exception {
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = options.getCompositeFilter();
        QueryNonFilterConstraint[] queryConstraints = options.getQueryConstraints();

        Query query = this.firestoreInstance.collection(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, this.firestoreInstance);
            }
        }
        query
            .get()
            .addOnSuccessListener(
                querySnapshot -> {
                    GetCollectionResult result = new GetCollectionResult(querySnapshot);
                    callback.success(result);
                }
            )
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getCollectionGroup(@NonNull GetCollectionGroupOptions options, @NonNull NonEmptyResultCallback callback) throws Exception {
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = options.getCompositeFilter();
        QueryNonFilterConstraint[] queryConstraints = options.getQueryConstraints();

        Query query = this.firestoreInstance.collectionGroup(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, this.firestoreInstance);
            }
        }
        query
            .get()
            .addOnSuccessListener(
                querySnapshot -> {
                    GetCollectionResult result = new GetCollectionResult(querySnapshot);
                    callback.success(result);
                }
            )
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void clearPersistence(@NonNull EmptyResultCallback callback) {
        this.firestoreInstance.clearPersistence()
            .addOnSuccessListener(
                unused -> {
                    callback.success();
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public void enableNetwork(@NonNull EmptyResultCallback callback) {
        this.firestoreInstance.enableNetwork()
            .addOnSuccessListener(
                unused -> {
                    callback.success();
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public void disableNetwork(@NonNull EmptyResultCallback callback) {
        this.firestoreInstance.disableNetwork()
            .addOnSuccessListener(
                unused -> {
                    callback.success();
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public void addDocumentSnapshotListener(@NonNull AddDocumentSnapshotListenerOptions options, @NonNull NonEmptyResultCallback callback) {
        String reference = options.getReference();
        String callbackId = options.getCallbackId();

        ListenerRegistration listenerRegistration =
            this.firestoreInstance.document(reference)
                .addSnapshotListener(
                    (documentSnapshot, exception) -> {
                        if (exception != null) {
                            callback.error(exception);
                        } else {
                            GetDocumentResult result = new GetDocumentResult(documentSnapshot);
                            callback.success(result);
                        }
                    }
                );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void addCollectionSnapshotListener(
        @NonNull AddCollectionSnapshotListenerOptions options,
        @NonNull NonEmptyResultCallback callback
    ) throws Exception {
        String reference = options.getReference();
        QueryCompositeFilterConstraint compositeFilter = options.getCompositeFilter();
        QueryNonFilterConstraint[] queryConstraints = options.getQueryConstraints();
        String callbackId = options.getCallbackId();

        Query query = this.firestoreInstance.collection(reference);
        if (compositeFilter != null) {
            Filter filter = compositeFilter.toFilter();
            query = query.where(filter);
        }
        if (queryConstraints.length > 0) {
            for (QueryNonFilterConstraint queryConstraint : queryConstraints) {
                query = queryConstraint.toQuery(query, this.firestoreInstance);
            }
        }

        ListenerRegistration listenerRegistration = query.addSnapshotListener(
            (querySnapshot, exception) -> {
                if (exception != null) {
                    callback.error(exception);
                } else {
                    GetCollectionResult result = new GetCollectionResult(querySnapshot);
                    callback.success(result);
                }
            }
        );
        this.listenerRegistrationMap.put(callbackId, listenerRegistration);
    }

    public void removeSnapshotListener(@NonNull RemoveSnapshotListenerOptions options) {
        String callbackId = options.getCallbackId();

        ListenerRegistration listenerRegistration = this.listenerRegistrationMap.get(callbackId);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.remove(callbackId);
    }

    public void removeAllListeners() {
        for (ListenerRegistration listenerRegistration : this.listenerRegistrationMap.values()) {
            listenerRegistration.remove();
        }
        this.listenerRegistrationMap.clear();
    }
}
