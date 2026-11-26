package io.capawesome.capacitorjs.plugins.firebase.app.classes;

public class CustomExceptions {

    public static final CustomException API_KEY_MISSING = new CustomException(null, "options.apiKey must be provided.");
    public static final CustomException APP_ALREADY_EXISTS = new CustomException(null, "An app with this name already exists.");
    public static final CustomException APPLICATION_ID_MISSING = new CustomException(null, "options.applicationId must be provided.");
    public static final CustomException GCM_SENDER_ID_MISSING = new CustomException(null, "options.gcmSenderId must be provided.");
    public static final CustomException NAME_INVALID = new CustomException(
        null,
        "name must only contain letters, numbers, hyphens and underscores."
    );
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException OPTIONS_MISSING = new CustomException(null, "options must be provided.");
    public static final CustomException PROJECT_ID_MISSING = new CustomException(null, "options.projectId must be provided.");
}
