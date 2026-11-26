package io.capawesome.capacitorjs.plugins.firebase.functions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.functions.FirebaseFunctionsException;
import java.util.Locale;

public class FirebaseFunctionsHelper {

    @Nullable
    public static String createErrorCode(@Nullable Exception exception) {
        if (!(exception instanceof FirebaseFunctionsException)) {
            return null;
        }
        FirebaseFunctionsException.Code errorCode = ((FirebaseFunctionsException) exception).getCode();
        return "functions/" + convertErrorCodeToString(errorCode);
    }

    @NonNull
    private static String convertErrorCodeToString(@NonNull FirebaseFunctionsException.Code errorCode) {
        return errorCode.name().replace('_', '-').toLowerCase(Locale.ROOT);
    }
}
