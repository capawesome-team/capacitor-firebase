package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

public class ConfirmVerificationCodeOptions {

    private String verificationId;
    private String verificationCode;

    public ConfirmVerificationCodeOptions(String verificationId, String verificationCode) {
        this.verificationId = verificationId;
        this.verificationCode = verificationCode;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
