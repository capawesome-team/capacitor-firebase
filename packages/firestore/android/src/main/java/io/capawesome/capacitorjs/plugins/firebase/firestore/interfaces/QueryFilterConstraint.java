package io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces;

import androidx.annotation.Nullable;
import com.google.firebase.firestore.Filter;

public interface QueryFilterConstraint {
    @Nullable
    Filter toFilter();
}
