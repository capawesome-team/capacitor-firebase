package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SignInWithPhoneNumberOptions extends SignInOptions {

    private String phoneNumber;
    private boolean resendCode;

    @NonNull
    private Long timeout;

    public SignInWithPhoneNumberOptions(boolean skipNativeAuth, String phoneNumber, boolean resendCode, @NonNull Long timeout) {
        super(skipNativeAuth);
        this.phoneNumber = phoneNumber;
        this.resendCode = resendCode;
        this.timeout = timeout;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getResendCode() {
        return resendCode;
    }

    @NonNull
    public Long getTimeout() {
        return timeout;
    }
}
