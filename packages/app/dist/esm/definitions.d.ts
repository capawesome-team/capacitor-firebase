export interface FirebaseAppPlugin {
    /**
     * Get the name for this app.
     *
     * @since 0.1.0
     */
    getName(): Promise<GetNameResult>;
    /**
     * Get the configuration options for this app.
     *
     * @since 0.1.0
     */
    getOptions(): Promise<GetOptionsResult>;
}
/**
 * @since 0.1.0
 */
export interface GetNameResult {
    /**
     * The unique name of this app.
     *
     * @since 0.1.0
     */
    name: string;
}
/**
 * @since 0.1.0
 */
export interface GetOptionsResult {
    /**
     * API key used for authenticating requests from your app.
     *
     * @since 0.1.0
     */
    apiKey: string;
    /**
     * Google App ID used to uniquely identify an instance of an app.
     *
     * @since 0.1.0
     */
    applicationId: string;
    /**
     * The database root URL.
     *
     * @since 0.1.0
     */
    databaseUrl: string;
    /**
     * The Project Number.
     *
     * @since 0.1.0
     */
    gcmSenderId: string;
    /**
     * The Google Cloud project ID.
     *
     * @since 0.1.0
     */
    projectId: string;
    /**
     * The Google Cloud Storage bucket name.
     *
     * @since 0.1.0
     */
    storageBucket: string;
}
