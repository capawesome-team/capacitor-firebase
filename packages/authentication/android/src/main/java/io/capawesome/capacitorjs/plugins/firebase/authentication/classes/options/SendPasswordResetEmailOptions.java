package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.google.firebase.auth.ActionCodeSettings;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;

public class SendPasswordResetEmailOptions {
    @NonNull
    private String email;

    @Nullable
    private ActionCodeSettings actionCodeSettings;

    public SendPasswordResetEmailOptions(@NonNull String email, @Nullable JSObject actionCodeSettings) throws JSONException {
        this.actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettingsFromJSObject(actionCodeSettings);
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public ActionCodeSettings getActionCodeSettings() {
        return actionCodeSettings;
    }
}
