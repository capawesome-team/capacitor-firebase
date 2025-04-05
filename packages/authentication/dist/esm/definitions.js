/// <reference types="@capacitor/cli" />
/**
 * @since 5.2.0
 */
export var Persistence;
(function (Persistence) {
    /**
     * Long term persistence using IndexedDB.
     *
     * @since 5.2.0
     */
    Persistence["IndexedDbLocal"] = "INDEXED_DB_LOCAL";
    /**
     * No persistence.
     *
     * @since 5.2.0
     */
    Persistence["InMemory"] = "IN_MEMORY";
    /**
     * Long term persistence using local storage.
     *
     * @since 5.2.0
     */
    Persistence["BrowserLocal"] = "BROWSER_LOCAL";
    /**
     * Temporary persistence using session storage.
     *
     * @since 5.2.0
     */
    Persistence["BrowserSession"] = "BROWSER_SESSION";
})(Persistence || (Persistence = {}));
export var ProviderId;
(function (ProviderId) {
    ProviderId["APPLE"] = "apple.com";
    ProviderId["FACEBOOK"] = "facebook.com";
    ProviderId["GAME_CENTER"] = "gc.apple.com";
    ProviderId["GITHUB"] = "github.com";
    ProviderId["GOOGLE"] = "google.com";
    ProviderId["MICROSOFT"] = "microsoft.com";
    ProviderId["PLAY_GAMES"] = "playgames.google.com";
    ProviderId["TWITTER"] = "twitter.com";
    ProviderId["YAHOO"] = "yahoo.com";
    ProviderId["PASSWORD"] = "password";
    ProviderId["PHONE"] = "phone";
})(ProviderId || (ProviderId = {}));
//# sourceMappingURL=definitions.js.map