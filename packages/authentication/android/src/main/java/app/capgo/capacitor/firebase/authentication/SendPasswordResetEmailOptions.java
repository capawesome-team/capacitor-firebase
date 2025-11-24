package app.capgo.capacitor.firebase.authentication.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.ActionCodeSettings;
import app.capgo.capacitor.firebase.authentication.FirebaseAuthenticationHelper;
import org.json.JSONException;

public class SendPasswordResetEmailOptions {

    @NonNull
    private String email;

    @Nullable
    private ActionCodeSettings actionCodeSettings;

    public SendPasswordResetEmailOptions(@NonNull String email, @Nullable JSObject actionCodeSettings) throws JSONException {
        this.email = email;
        this.actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(actionCodeSettings);
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
