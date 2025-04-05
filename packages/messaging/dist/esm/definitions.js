/// <reference types="@capacitor/cli" />
/**
 * The importance level.
 *
 * For more details, see the [Android Developer Docs](https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_DEFAULT)
 *
 * @since 1.4.0
 */
export var Importance;
(function (Importance) {
    /**
     * @since 1.4.0
     */
    Importance[Importance["Min"] = 1] = "Min";
    /**
     * @since 1.4.0
     */
    Importance[Importance["Low"] = 2] = "Low";
    /**
     * @since 1.4.0
     */
    Importance[Importance["Default"] = 3] = "Default";
    /**
     * @since 1.4.0
     */
    Importance[Importance["High"] = 4] = "High";
    /**
     * @since 1.4.0
     */
    Importance[Importance["Max"] = 5] = "Max";
})(Importance || (Importance = {}));
/**
 * The notification visibility.
 *
 * For more details, see the [Android Developer Docs](https://developer.android.com/reference/androidx/core/app/NotificationCompat#VISIBILITY_PRIVATE())
 *
 * @since 1.4.0
 */
export var Visibility;
(function (Visibility) {
    /**
     * @since 1.4.0
     */
    Visibility[Visibility["Secret"] = -1] = "Secret";
    /**
     * @since 1.4.0
     */
    Visibility[Visibility["Private"] = 0] = "Private";
    /**
     * @since 1.4.0
     */
    Visibility[Visibility["Public"] = 1] = "Public";
})(Visibility || (Visibility = {}));
//# sourceMappingURL=definitions.js.map