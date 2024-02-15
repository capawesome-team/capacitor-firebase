package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import com.google.firebase.firestore.Filter;
import javax.annotation.Nullable;

public interface QueryFilterConstraint {
    @Nullable
    Filter toFilter();
}
