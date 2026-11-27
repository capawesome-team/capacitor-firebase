package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes;

public class CustomExceptions {

    public static final CustomException ENABLED_MISSING = new CustomException(null, "enabled must be provided.");
    public static final CustomException KEY_MISSING = new CustomException(null, "key must be provided.");
    public static final CustomException MESSAGE_MISSING = new CustomException(null, "message must be provided.");
    public static final CustomException USER_ID_MISSING = new CustomException(null, "userId must be provided.");
    public static final CustomException VALUE_INVALID = new CustomException(null, "value is invalid.");
    public static final CustomException VALUE_MISSING = new CustomException(null, "value must be provided.");
}
