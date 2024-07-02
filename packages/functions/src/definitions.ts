export interface FirebaseFunctionsPlugin {
  /**
   * Call a callable function by name.
   *
   * @since 6.1.0
   */
  callByName(options: CallByNameOptions): Promise<CallByNameResult>;
  /**
   * Call a callable function by URL.
   *
   * @since 6.1.0
   */
  callByUrl(options: CallByUrlOptions): Promise<CallByUrlResult>;
  /**
   * Instrument your app to talk to the Cloud Functions emulator.
   *
   * On Android, the cleartext traffic must be allowed. On the Capacitor configuration:
   * ```
   * {
   *   server: {
   *     cleartext: true
   *   }
   * }
   * ```
   * **The cleartext traffic is not intended for use in production.**
   *
   * @since 6.1.0
   */
  useEmulator(options: UseEmulatorOptions): Promise<void>;
}

/**
 * @since 6.1.0
 */
export interface CallByNameOptions extends CallOptions {
  /**
   * The name of the callable function.
   *
   * @example 'myFunction'
   * @since 6.1.0
   */
  name: string;
  /**
   * The region of the callable function.
   *
   * @example 'us-central1'
   * @since 6.1.0
   */
  region?: string;
}

/**
 * @since 6.1.0
 */
export type CallByNameResult = CallResult;

/**
 * @since 6.1.0
 */
export interface CallByUrlOptions extends CallOptions {
  /**
   * The URL of the callable function.
   *
   * @example 'https://us-central1-my-project.cloudfunctions.net/myFunction'
   * @since 6.1.0
   */
  url: string;
}

/**
 * @since 6.1.0
 */
export type CallByUrlResult = CallResult;

/**
 * @since 6.1.0
 */
export interface CallOptions {
  /**
   * The data to pass to the callable function.
   *
   * @since 6.1.0
   */
  data?: any;
}

/**
 * @since 6.1.0
 */
export interface CallResult {
  /**
   * The result of the callable function.
   *
   * @since 6.1.0
   */
  data?: any;
}

/**
 * @since 6.1.0
 */
export interface UseEmulatorOptions {
  /**
   * The emulator host without any port or scheme.
   *
   * Note when using a Android Emulator device: 10.0.2.2 is the special IP address to connect to the 'localhost' of the host computer.
   *
   * @since 6.1.0
   * @example "127.0.0.1"
   */
  host: string;
  /**
   * The emulator port.
   *
   * @since 6.1.0
   * @default 5001
   * @example 5001
   */
  port?: number;
}
