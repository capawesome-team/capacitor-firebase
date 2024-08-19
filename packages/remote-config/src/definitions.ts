export interface FirebaseRemoteConfigPlugin {
  /**
   * Make the last fetched configuration available to the getters.
   *
   * @since 1.3.0
   */
  activate(): Promise<void>;
  /**
   * Perform fetch and activate operations.
   *
   * @since 1.3.0
   */
  fetchAndActivate(): Promise<void>;
  /**
   * Fetch and cache configuration from the Remote Config service.
   *
   * @since 1.3.0
   */
  fetchConfig(options?: FetchConfigOptions): Promise<void>;
  /**
   * Get the value for the given key as a boolean.
   *
   * @since 1.3.0
   */
  getBoolean(options: GetBooleanOptions): Promise<GetBooleanResult>;
  /**
   * Get the value for the given key as a number.
   *
   * @since 1.3.0
   */
  getNumber(options: GetNumberOptions): Promise<GetNumberResult>;
  /**
   * Get the value for the given key as a string.
   *
   * @since 1.3.0
   */
  getString(options: GetStringOptions): Promise<GetStringResult>;
  /**
   * Set the minimum fetch interval.
   *
   * Only available for Web.
   *
   * @since 1.3.0
   */
  setMinimumFetchInterval(
    options: SetMinimumFetchIntervalOptions,
  ): Promise<void>;
  /**
   * Set the remote config settings.
   * 
   * On Android, the settings values are persisted in SharedPreferences.
   *
   * @since 6.2.0
   */
  setSettings(options: SetSettingsOptions): Promise<void>;
  /**
   * Add a listener for the config update event.
   *
   * Only available for Android and iOS.
   *
   * @since 5.4.0
   */
  addConfigUpdateListener(
    callback: AddConfigUpdateListenerOptionsCallback,
  ): Promise<CallbackId>;
  /**
   * Remove a listener for the config update event.
   *
   * Only available for Android and iOS.
   *
   * @since 5.4.0
   */
  removeConfigUpdateListener(
    options: RemoveConfigUpdateListenerOptions,
  ): Promise<void>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 5.4.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 1.3.0
 */
export interface GetOptions {
  /**
   * The key of the value to get.
   *
   * @since 1.3.0
   */
  key: string;
}

/**
 * @since 1.3.0
 */
export interface FetchConfigOptions {
  /**
   * Define the maximum age in seconds of an entry in the config cache before it is considered stale.
   * During development, it's recommended to set a relatively low minimum fetch interval.
   *
   * Only available for Android and iOS.
   *
   * @since 1.3.0
   * @default 43200
   * @see https://firebase.google.com/docs/reference/js/firebase.remoteconfig.RemoteConfigSettings#minimumfetchintervalmillis
   */
  minimumFetchIntervalInSeconds?: number;
}

/**
 * @since 1.3.0
 */
export type GetBooleanOptions = GetOptions;

/**
 * @since 1.3.0
 */
export type GetNumberOptions = GetOptions;

/**
 * @since 1.3.0
 */
export type GetStringOptions = GetOptions;

/**
 * @since 1.3.0
 */
export interface GetBooleanResult {
  /**
   * The value for the given key as a boolean.
   *
   * @since 1.3.0
   */
  value: boolean;
  /**
   * Indicates at which source this value came from.
   *
   * Only available for Android and iOS.
   *
   * @since 1.3.0
   */
  source?: GetValueSource;
}

/**
 * @since 1.3.0
 */
export interface GetNumberResult {
  /**
   * The value for the given key as a number.
   *
   * @since 1.3.0
   */
  value: number;
  /**
   * Indicates at which source this value came from.
   *
   * Only available for Android and iOS.
   *
   * @since 1.3.0
   */
  source?: GetValueSource;
}

/**
 * @since 1.3.0
 */
export interface GetStringResult {
  /**
   * The value for the given key as a string.
   *
   * @since 1.3.0
   */
  value: string;
  /**
   * Indicates at which source this value came from.
   *
   * Only available for Android and iOS.
   *
   * @since 1.3.0
   */
  source?: GetValueSource;
}

/**
 * @since 1.3.0
 */
export interface SetMinimumFetchIntervalOptions {
  /**
   * Define the maximum age in seconds of an entry in the config cache before it is considered stale.
   * During development, it's recommended to set a relatively low minimum fetch interval.
   *
   * @since 1.3.0
   * @default 43200
   * @see https://firebase.google.com/docs/reference/js/remote-config.remoteconfigsettings#remoteconfigsettingsminimumfetchintervalmillis
   */
  minimumFetchIntervalInSeconds: number;
}

/**
 * @since 6.2.0
 */
export interface SetSettingsOptions {
  /**
   * Defines the maximum amount of milliseconds to wait for a response when fetching configuration from the Remote Config server.
   *
   * @since 6.2.0
   * @default 60
   * @see https://firebase.google.com/docs/reference/js/remote-config.remoteconfigsettings#remoteconfigsettingsfetchtimeoutmillis
   */
  fetchTimeoutInSeconds?: number;
  /**
   * Define the maximum age in seconds of an entry in the config cache before it is considered stale.
   * During development, it's recommended to set a relatively low minimum fetch interval.
   *
   * @since 6.2.0
   * @default 43200
   * @see https://firebase.google.com/docs/reference/js/remote-config.remoteconfigsettings#remoteconfigsettingsminimumfetchintervalmillis
   */
  minimumFetchIntervalInSeconds?: number;
}

/**
 * @since 5.4.0
 */
export type AddConfigUpdateListenerOptionsCallback = (
  event: AddConfigUpdateListenerOptionsCallbackEvent | null,
  error: any,
) => void;

/**
 * @since 5.4.0
 */
export interface AddConfigUpdateListenerOptionsCallbackEvent {
  /**
   * Parameter keys whose values have been updated from the currently activated values.
   *
   * @since 5.4.0
   */
  updatedKeys: string[];
}

/**
 * @since 5.4.0
 */
export type CallbackId = string;

/**
 * @since 5.4.0
 */
export interface RemoveConfigUpdateListenerOptions {
  /**
   * The id of the listener to remove.
   *
   * @since 5.4.0
   */
  id: CallbackId;
}

/**
 * @since 1.3.0
 */
export enum GetValueSource {
  /**
   * Indicates that the value returned is the static default value.
   *
   * @since 1.3.0
   */
  Static = 0,
  /**
   * Indicates that the value returned was retrieved from the defaults set by the client.
   *
   * @since 1.3.0
   */
  Default = 1,
  /**
   * Indicates that the value returned was retrieved from the Firebase Remote Config Server.
   *
   * @since 1.3.0
   */
  Remote = 2,
}
