/**
 * @since 1.3.0
 */
export var GetValueSource;
(function (GetValueSource) {
    /**
     * Indicates that the value returned is the static default value.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Static"] = 0] = "Static";
    /**
     * Indicates that the value returned was retrieved from the defaults set by the client.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Default"] = 1] = "Default";
    /**
     * Indicates that the value returned was retrieved from the Firebase Remote Config Server.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Remote"] = 2] = "Remote";
})(GetValueSource || (GetValueSource = {}));
//# sourceMappingURL=definitions.js.map