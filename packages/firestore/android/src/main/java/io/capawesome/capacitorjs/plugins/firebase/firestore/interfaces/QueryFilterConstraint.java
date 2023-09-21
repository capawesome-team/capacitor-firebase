package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import com.google.firebase.firestore.Filter;

public interface QueryFilterConstraint {
    Filter toFilter();
}
