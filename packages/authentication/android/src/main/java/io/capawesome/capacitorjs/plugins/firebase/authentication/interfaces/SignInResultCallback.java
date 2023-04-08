package io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces;

import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInResult;

public interface SignInResultCallback {
    void success(SignInResult result);
    void error(Exception exception);
}
