package io.capawesome.capacitorjs.plugins.firebase.analytics;

import androidx.annotation.Nullable;

public interface GetSessionIdCallback {
    void success(@Nullable Long appSessionId);
    void error(String message);
}
