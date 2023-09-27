package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

public class SignInOptions {

    private boolean skipNativeAuth;

    public SignInOptions(boolean skipNativeAuth) {
        this.skipNativeAuth = skipNativeAuth;
    }

    public boolean getSkipNativeAuth() {
        return skipNativeAuth;
    }
}
