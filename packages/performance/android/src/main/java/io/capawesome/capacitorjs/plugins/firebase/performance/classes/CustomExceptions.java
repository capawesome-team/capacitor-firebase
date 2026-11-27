package io.capawesome.capacitorjs.plugins.firebase.performance.classes;

public class CustomExceptions {

    public static final CustomException ATTRIBUTE_MISSING = new CustomException(null, "attribute must be provided.");
    public static final CustomException ENABLED_MISSING = new CustomException(null, "enabled must be provided.");
    public static final CustomException METRIC_NAME_MISSING = new CustomException(null, "metricName must be provided.");
    public static final CustomException NUM_MISSING = new CustomException(null, "num must be provided.");
    public static final CustomException TRACE_NAME_ALREADY_ASSIGNED = new CustomException(null, "traceName already assigned.");
    public static final CustomException TRACE_NAME_INVALID = new CustomException(null, "traceName is invalid.");
    public static final CustomException TRACE_NAME_MISSING = new CustomException(null, "traceName must be provided.");
    public static final CustomException TRACE_NOT_FOUND = new CustomException(null, "No trace was found with the provided traceName.");
    public static final CustomException VALUE_MISSING = new CustomException(null, "value must be provided.");
}
