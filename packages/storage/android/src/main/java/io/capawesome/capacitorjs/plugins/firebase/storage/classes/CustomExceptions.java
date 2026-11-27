package io.capawesome.capacitorjs.plugins.firebase.storage.classes;

public class CustomExceptions {

    public static final CustomException HOST_MISSING = new CustomException(null, "host must be provided.");
    public static final CustomException METADATA_MISSING = new CustomException(null, "metadata must be provided.");
    public static final CustomException PATH_MISSING = new CustomException(null, "path must be provided.");
    public static final CustomException URI_MISSING = new CustomException(null, "uri must be provided.");
}
