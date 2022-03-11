export interface FirebaseAppPlugin {
  /**
   * Get the name for this app.
   */
  getName(): Promise<GetNameResult>;
  /**
   * Get the configuration options for this app.
   */
  getOptions(): Promise<GetOptionsResult>;
}

export interface GetNameResult {
  /**
   * The unique name of this app.
   */
  name: string;
}

export interface GetOptionsResult {
  /**
   * API key used for authenticating requests from your app.
   */
  apiKey: string;
  /**
   * Google App ID used to uniquely identify an instance of an app.
   */
  applicationId: string;
  /**
   * The database root URL.
   */
  databaseUrl: string;
  /**
   * The Project Number.
   */
  gcmSenderId: string;
  /**
   * The Google Cloud project ID.
   */
  projectId: string;
  /**
   * The Google Cloud Storage bucket name.
   */
  storageBucket: string;
}
