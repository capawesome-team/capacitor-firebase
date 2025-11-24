package app.capgo.capacitor.firebase.authentication.classes;

public class SignInOptions {

    private boolean skipNativeAuth;

    public SignInOptions(boolean skipNativeAuth) {
        this.skipNativeAuth = skipNativeAuth;
    }

    public boolean getSkipNativeAuth() {
        return skipNativeAuth;
    }
}
