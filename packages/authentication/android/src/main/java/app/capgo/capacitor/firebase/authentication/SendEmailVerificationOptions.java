package app.capgo.capacitor.firebase.authentication.classes.options;

import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.authentication.FirebaseAuthenticationHelper;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.ActionCodeSettings;
import org.json.JSONException;

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
