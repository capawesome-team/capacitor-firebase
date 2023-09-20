package io.capawesome.capacitorjs.plugins.firebase.firestore;

import android.util.Log;

public class FirebaseFirestore {
    private FirebaseFirestorePlugin plugin;
    private FirebaseAuth firebaseAuthInstance;

    public FirebaseFirestore(FirebaseFirestorePlugin plugin) {
        this.plugin = plugin;
        firebaseAuthInstance = FirebaseAuth.getInstance();
    }

    public void addDocument(@NonNull AddDocumentOptions options, @NonNull ResultCallback callback) {
        
    }

    public void setDocument(@NonNull SetDocumentOptions options, @NonNull ResultCallback callback) {
        
    }

    public void getDocument(@NonNull GetDocumentOptions options, @NonNull ResultCallback callback) {
        
    }

    public void updateDocument(@NonNull UpdateDocumentOptions options, @NonNull ResultCallback callback) {
        
    }

    public void deleteDocument(@NonNull DeleteDocumentOptions options, @NonNull ResultCallback callback) {
        
    }

    public void getCollection(@NonNull GetCollectionOptions options, @NonNull ResultCallback callback) {
        
    }

    public void getCollectionGroup(@NonNull GetCollectionGroupOptions options, @NonNull ResultCallback callback) {
        
    }

    public void enableNetwork() {
        
    }

    public void disableNetwork() {
        
    }

    public void addDocumentSnapshotListener(@NonNull AddDocumentSnapshotListenerOptions options) {
        
    }

    public void removeSnapshotListener(@NonNull RemoveSnapshotListenerOptions options) {
        
    }

    public void removeAllListeners() {
        
    }
}
