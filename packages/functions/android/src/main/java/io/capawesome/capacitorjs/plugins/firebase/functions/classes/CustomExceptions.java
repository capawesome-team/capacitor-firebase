package io.capawesome.capacitorjs.plugins.firebase.functions.classes;

public class CustomExceptions {

    public static final CustomException HOST_MISSING = new CustomException(null, "host must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException URL_MISSING = new CustomException(null, "url must be provided.");
}
