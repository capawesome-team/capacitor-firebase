export interface InitializationOptions {
  /**
   * If debug is enabled, the native layer will use debug mode and log a debug token to the console.
   * This can be used to add to the App Check debug tokens list in the Firebase console and should only
   * be used for testing.
   * 
   * @since 1.0.0
   */
  debug: boolean;
}

export type AppCheckToken = {
  /**
   * The token provided by the native environment to be sent to firebase.
   *
   * @since 1.0.0
   */
  token: string;

  /**
   * The expiration date of the token in milliseconds since epoch [Unix time]
   * 
   * @since 1.0.0
   */
  exp: number;
}

export interface FirebaseAppCheckPlugin {
  /**
   * Initialise the App Check plugin. This must be called once (and once only) before
   * calling any other method. Returns boolean of success or failure. Check the native logs
   * for more information in the event of a failure.
   * @usage
   * ```typescript
   * const success = await AppCheck.initialize({
   *   debug: false
   * });
   * ```
   * 
   * @since 1.0.0
   */
  initialize(options?: InitializationOptions): Promise<{ success: boolean }>;

  /**
   * Get's the native AppCheck token from DeviceCheck/AppAttest on iOS or Play Integrity on Android.
   * @usage
   * ```typescript
   * const { token } = await FirebaseAppCheck.getToken();
   * ```
   * 
   * @since 1.0.0
   */
  getToken(): Promise<AppCheckToken>;
}
