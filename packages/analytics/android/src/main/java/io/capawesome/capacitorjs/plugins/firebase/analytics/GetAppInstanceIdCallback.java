package io.capawesome.capacitorjs.plugins.firebase.analytics;

import androidx.annotation.Nullable;

public interface GetAppInstanceIdCallback {
    void success(@Nullable String appInstanceId);
    void error(String message);
}
