package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import com.google.firebase.firestore.Query;

public interface QueryNonFilterConstraint {
    Query toQuery(Query query, com.google.firebase.firestore.FirebaseFirestore firestoreInstance) throws Exception;
}
