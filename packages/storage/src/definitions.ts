export interface FirebaseStoragePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
