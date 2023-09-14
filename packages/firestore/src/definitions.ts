export interface FirebaseFirestorePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
