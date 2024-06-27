package io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options;

import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.google.firebase.auth.ActionCodeSettings;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;

public class SendEmailVerificationOptions {
    @Nullable
    private ActionCodeSettings actionCodeSettings;

    public SendEmailVerificationOptions(@Nullable JSObject actionCodeSettings) throws JSONException {
        this.actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(actionCodeSettings);
    }

    @Nullable
    public ActionCodeSettings getActionCodeSettings() {
        return actionCodeSettings;
    }
}
