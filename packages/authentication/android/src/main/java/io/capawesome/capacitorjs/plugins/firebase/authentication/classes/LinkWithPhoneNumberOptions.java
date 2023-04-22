package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

public class LinkWithPhoneNumberOptions extends SignInWithPhoneNumberOptions {

    public LinkWithPhoneNumberOptions(String phoneNumber, boolean resendCode) {
        super(false, phoneNumber, resendCode);
    }
}
