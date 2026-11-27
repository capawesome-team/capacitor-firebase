package io.capawesome.capacitorjs.plugins.firebase.analytics.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomException extends Exception {

    @Nullable
    private final String code;

    public CustomException(@Nullable String code, @NonNull String message) {
        super(message);
        this.code = code;
    }

    @Nullable
    public String getCode() {
        return code;
    }
}
