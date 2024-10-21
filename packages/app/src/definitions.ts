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
  /**
   * Initialize a new Firebase App with the provided name and Firebase project configuration.
   *
   * @since 6.2.0
   */
  initializeApp(options: InitializeAppOptions): Promise<void>;
  /**
   * Get all initialized Firebase apps.
   *
   * @since 6.2.0
   */
  getApps(): Promise<GetAppsResult>;
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

/**
 * @since 6.2.0
 */
export interface InitializeAppOptions {
  /**
   * Name of the app to initialize.
   *
   * @since 6.2.0
   */
  name: string;
  config: {
    /**
     * API key used for authenticating requests from your app.
     *
     * @since 6.2.0
     */
    apiKey: string;
    /**
     * Google App ID used to uniquely identify an instance of an app.
     *
     * @since 6.2.0
     */
    appId: string;
    /**
     * The Google Cloud project ID.
     *
     * @since 6.2.0
     */
    projectId: string;
    /**
     * The Google Cloud Storage bucket name.
     *
     * @since 6.2.0
     */
    storageBucket: string;
    /**
     * The database root URL.
     *
     * @since 6.2.0
     */
    databaseURL: string;
    /**
     * The Project Number.
     *
     * @since 6.2.0
     */
    messagingSenderId: string;
    /**
     * The client ID.
     *
     * @since 6.2.0
     */
    clientId: string;
  };
}

/**
 * @since 6.2.0
 */
export interface GetAppsResult {
  /**
   * An array of names of all initialized apps
   *
   * @since 6.2.0
   */
  apps: string[];
}
