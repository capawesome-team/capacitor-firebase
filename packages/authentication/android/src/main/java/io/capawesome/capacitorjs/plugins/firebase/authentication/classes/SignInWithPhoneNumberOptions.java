package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

public class SignInWithPhoneNumberOptions extends SignInOptions {

    private String phoneNumber;
    private boolean resendCode;

    public SignInWithPhoneNumberOptions(boolean skipNativeAuth, String phoneNumber, boolean resendCode) {
        super(skipNativeAuth);
        this.phoneNumber = phoneNumber;
        this.resendCode = resendCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getResendCode() {
        return resendCode;
    }
}
