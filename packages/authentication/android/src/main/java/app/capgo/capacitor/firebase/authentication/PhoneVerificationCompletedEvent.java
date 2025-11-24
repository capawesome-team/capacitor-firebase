package app.capgo.capacitor.firebase.authentication.classes;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class PhoneVerificationCompletedEvent extends SignInResult {

    @Nullable
    private String smsCode;

    public PhoneVerificationCompletedEvent(
        @Nullable FirebaseUser user,
        @Nullable AuthCredential credential,
        @Nullable AdditionalUserInfo additionalUserInfo,
        @Nullable String smsCode
    ) {
        super(user, credential, additionalUserInfo);
        this.smsCode = smsCode;
    }

    @Nullable
    public String getSmsCode() {
        return smsCode;
    }

    public JSObject toJSObject() {
        JSObject result = super.toJSObject();
        result.put("verificationCode", this.smsCode);
        return result;
    }
}
