package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomExceptions;

public class FetchSignInMethodsForEmailOptions {

    @NonNull
    private final String email;

    public FetchSignInMethodsForEmailOptions(@NonNull PluginCall call) throws Exception {
        this.email = FetchSignInMethodsForEmailOptions.getEmailFromCall(call);
    }

    @NonNull
    public String getEmail() {
        return this.email;
    }

    @NonNull
    private static String getEmailFromCall(@NonNull PluginCall call) throws Exception {
        String email = call.getString("email");
        if (email == null) {
            throw CustomExceptions.EMAIL_MISSING;
        }
        return email;
    }
}
