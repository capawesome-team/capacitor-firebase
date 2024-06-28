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
