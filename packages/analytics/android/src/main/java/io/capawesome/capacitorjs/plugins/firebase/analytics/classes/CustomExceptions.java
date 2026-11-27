package io.capawesome.capacitorjs.plugins.firebase.analytics.classes;

public class CustomExceptions {

    public static final CustomException CONSENT_STATUS_MISSING = new CustomException(null, "consentStatus must be provided.");
    public static final CustomException CONSENT_TYPE_MISSING = new CustomException(null, "consentType must be provided.");
    public static final CustomException ENABLED_MISSING = new CustomException(null, "enabled must be provided.");
    public static final CustomException KEY_MISSING = new CustomException(null, "key must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
}
