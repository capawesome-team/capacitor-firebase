package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import io.capawesome.capacitorjs.plugins.firebase.authentication.FirebaseAuthenticationHelper;

public class SignInResult {

    @Nullable
    private FirebaseUser user;

    @Nullable
    private AuthCredential credential;

    @Nullable
    private AdditionalUserInfo additionalUserInfo;

    public SignInResult(@NonNull AuthResult authResult) {
        this.user = authResult.getUser();
        this.credential = authResult.getCredential();
        this.additionalUserInfo = authResult.getAdditionalUserInfo();
    }

    public SignInResult(@Nullable FirebaseUser user, @Nullable AuthCredential credential, @Nullable AdditionalUserInfo additionalUserInfo) {
        this.user = user;
        this.credential = credential;
        this.additionalUserInfo = additionalUserInfo;
    }

    @Nullable
    public FirebaseUser getUser() {
        return user;
    }

    @Nullable
    public AuthCredential getCredential() {
        return credential;
    }

    @Nullable
    public AdditionalUserInfo getAdditionalUserInfo() {
        return additionalUserInfo;
    }

    public JSObject toJSObject() {
        return FirebaseAuthenticationHelper.createSignInResult(this.user, this.credential, null, null, null, this.additionalUserInfo);
    }
}
