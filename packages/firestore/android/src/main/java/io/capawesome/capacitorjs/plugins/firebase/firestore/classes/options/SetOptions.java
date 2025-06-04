package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class SetOptions {

    private boolean merge;

    public SetOptions(@NonNull JSObject options) {
        // default to false if "merge" is not provided
        this.merge = options.optBoolean("merge", false);
    }

    public boolean isMerge() {
        return merge;
    }
}