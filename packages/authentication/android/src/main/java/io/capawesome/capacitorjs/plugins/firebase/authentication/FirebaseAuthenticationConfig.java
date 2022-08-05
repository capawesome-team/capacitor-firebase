package io.capawesome.capacitorjs.plugins.firebase.authentication;

public class FirebaseAuthenticationConfig {

    private boolean skipNativeAuth = false;
    private String[] providers = new String[] {};

    public boolean getSkipNativeAuth() {
        return skipNativeAuth;
    }

    public void setSkipNativeAuth(boolean skipNativeAuth) {
        this.skipNativeAuth = skipNativeAuth;
    }

    public String[] getProviders() {
        return providers;
    }

    public void setProviders(String[] providers) {
        this.providers = providers;
    }
}
