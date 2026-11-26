package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

public class CustomExceptions {

    public static final CustomException ACTION_CODE_SETTINGS_MISSING = new CustomException(null, "actionCodeSettings must be provided.");
    public static final CustomException CUSTOM_TOKEN_SKIP_NATIVE_AUTH = new CustomException(
        null,
        "signInWithCustomToken cannot be used in combination with skipNativeAuth."
    );
    public static final CustomException EMAIL_LINK_MISSING = new CustomException(null, "emailLink must be provided.");
    public static final CustomException EMAIL_LINK_SIGN_IN_SKIP_NATIVE_AUTH = new CustomException(
        null,
        "signInWithEmailLink cannot be used in combination with skipNativeAuth."
    );
    public static final CustomException EMAIL_LINK_SKIP_NATIVE_AUTH = new CustomException(
        null,
        "linkWithEmailAndPassword and linkWithEmailLink cannot be used in combination with skipNativeAuth."
    );
    public static final CustomException EMAIL_MISSING = new CustomException(null, "email must be provided.");
    public static final CustomException EMAIL_SIGN_IN_SKIP_NATIVE_AUTH = new CustomException(
        null,
        "createUserWithEmailAndPassword and signInWithEmailAndPassword cannot be used in combination with skipNativeAuth."
    );
    public static final CustomException HOST_MISSING = new CustomException(null, "host must be provided.");
    public static final CustomException NEW_EMAIL_MISSING = new CustomException(null, "newEmail must be provided.");
    public static final CustomException NEW_PASSWORD_MISSING = new CustomException(null, "newPassword must be provided.");
    public static final CustomException NO_USER_SIGNED_IN = new CustomException(null, "No user is signed in.");
    public static final CustomException OOB_CODE_MISSING = new CustomException(null, "oobCode must be provided.");
    public static final CustomException PASSWORD_MISSING = new CustomException(null, "password must be provided.");
    public static final CustomException PROVIDER_ID_MISSING = new CustomException(null, "providerId must be provided.");
    public static final CustomException SIGN_IN_ANONYMOUSLY_SKIP_NATIVE_AUTH = new CustomException(
        null,
        "signInAnonymously cannot be used in combination with skipNativeAuth."
    );
    public static final CustomException TENANT_ID_MISSING = new CustomException(null, "tenantId must be provided.");
    public static final CustomException TOKEN_MISSING = new CustomException(null, "token must be provided.");
}
