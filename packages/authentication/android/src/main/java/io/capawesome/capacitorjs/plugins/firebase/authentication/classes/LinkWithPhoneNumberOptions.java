package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.NonNull;

public class LinkWithPhoneNumberOptions extends SignInWithPhoneNumberOptions {

    public LinkWithPhoneNumberOptions(String phoneNumber, boolean resendCode, @NonNull Long timeout) {
        super(false, phoneNumber, resendCode, timeout);
    }
}
