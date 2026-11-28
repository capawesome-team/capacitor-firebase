package io.capawesome.capacitorjs.plugins.firebase.messaging.classes;

public class CustomExceptions {

    public static final CustomException GOOGLE_PLAY_SERVICES_UNAVAILABLE = new CustomException(
        null,
        "Google Play services are not available on this device."
    );
    public static final CustomException ID_MISSING = new CustomException(null, "id must be provided.");
    public static final CustomException ID_OR_NAME_MISSING = new CustomException(null, "id and name must be provided.");
    public static final CustomException NOTIFICATIONS_INVALID = new CustomException(null, "The provided notifications are invalid.");
    public static final CustomException NOTIFICATIONS_MISSING = new CustomException(null, "notifications must be provided.");
    public static final CustomException TOPIC_MISSING = new CustomException(null, "topic must be provided.");
}
