package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

public class CustomExceptions {

    public static final CustomException CALLBACK_ID_MISSING = new CustomException(null, "callbackId must be provided.");
    public static final CustomException DATA_MISSING = new CustomException(null, "data must be provided.");
    public static final CustomException HOST_MISSING = new CustomException(null, "host must be provided.");
    public static final CustomException OPERATIONS_MISSING = new CustomException(null, "operations must be provided.");
    public static final CustomException REFERENCE_MISSING = new CustomException(null, "reference must be provided.");
}
