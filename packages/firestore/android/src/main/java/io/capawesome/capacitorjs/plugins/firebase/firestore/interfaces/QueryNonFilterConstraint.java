package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import com.google.firebase.firestore.Query;

public interface QueryNonFilterConstraint<T extends Query> {
    T toQuery(T query);
}
