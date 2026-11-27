export interface FirebaseAppPlugin {
  /**
   * Get all initialized apps, including the default app.
   *
   * @since 8.7.0
   */
  getApps(): Promise<GetAppsResult>;
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
   * Initialize an additional app with its own name and configuration options.
   *
   * Pass the name as `appName` to the methods of a plugin that supports it,
   * such as Cloud Firestore, to use this app in place of the default app.
   *
   * On Android and iOS, the app exists until the native app is restarted
   * and survives a reload of the web view.
   * On Web, it exists until the page is reloaded.
   * The call is rejected if an app with the same name already exists,
   * so check `getApps()` first.
   *
   * @since 8.7.0
   */
  initializeApp(options: InitializeAppOptions): Promise<void>;
}

/**
 * @since 8.7.0
 */
export interface GetAppsResult {
  /**
   * The initialized apps, including the default app.
   *
   * @since 8.7.0
   */
  apps: App[];
}

/**
 * @since 8.7.0
 */
export interface App {
  /**
   * The unique name of the app.
   *
   * The name of the default app differs between the platforms.
   * Use `getName()` to get it.
   *
   * @since 8.7.0
   * @example 'secondary'
   */
  name: string;
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
   * Google app ID used to uniquely identify an instance of an app.
   *
   * @since 0.1.0
   */
  applicationId: string;
  /**
   * The database root URL.
   * An empty string if the configuration holds no value.
   *
   * @since 0.1.0
   */
  databaseUrl: string;
  /**
   * The Project Number.
   * An empty string if the configuration holds no value.
   *
   * @since 0.1.0
   */
  gcmSenderId: string;
  /**
   * The Google Cloud project ID.
   * An empty string if the configuration holds no value.
   *
   * @since 0.1.0
   */
  projectId: string;
  /**
   * The Google Cloud Storage bucket name.
   * An empty string if the configuration holds no value.
   *
   * @since 0.1.0
   */
  storageBucket: string;
}

/**
 * @since 8.7.0
 */
export interface InitializeAppOptions {
  /**
   * The unique name of the app.
   *
   * Only letters, numbers, hyphens and underscores are allowed.
   *
   * @since 8.7.0
   * @example 'secondary'
   */
  name: string;
  /**
   * The configuration options of the app.
   *
   * @since 8.7.0
   */
  options: FirebaseOptions;
}

/**
 * @since 8.7.0
 */
export interface FirebaseOptions {
  /**
   * API key used for authenticating requests from your app.
   *
   * On iOS, the value must have the format of a real API key:
   * the letter `A` followed by 38 letters, numbers, hyphens or underscores.
   *
   * @since 8.7.0
   */
  apiKey: string;
  /**
   * Google app ID used to uniquely identify an instance of an app.
   *
   * Every platform has its own app ID, so pass the one of the platform the app runs on.
   *
   * @since 8.7.0
   * @example '1:123456789012:android:abc123def456'
   */
  applicationId: string;
  /**
   * The database root URL.
   *
   * @since 8.7.0
   */
  databaseUrl?: string;
  /**
   * The Project Number.
   *
   * @since 8.7.0
   * @example '123456789012'
   */
  gcmSenderId: string;
  /**
   * The Google Cloud project ID.
   *
   * @since 8.7.0
   */
  projectId: string;
  /**
   * The Google Cloud Storage bucket name.
   *
   * @since 8.7.0
   */
  storageBucket?: string;
}
