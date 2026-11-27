package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes;

public class CustomExceptions {

    public static final CustomException CUSTOM_SIGNALS_MISSING = new CustomException(null, "customSignals must be provided.");
    public static final CustomException DEFAULTS_MISSING = new CustomException(null, "defaults must be provided.");
    public static final CustomException ID_MISSING = new CustomException(null, "id must be provided.");
    public static final CustomException KEY_MISSING = new CustomException(null, "key must be provided.");
    public static final CustomException NOT_AVAILABLE = new CustomException(null, "Not available on Android.");
    public static final CustomException SET_CUSTOM_SIGNALS_FAILED = new CustomException(null, "Failed to set custom signals.");
    public static final CustomException SET_DEFAULTS_FAILED = new CustomException(null, "Failed to set defaults.");
}
