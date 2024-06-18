export interface FunctionsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
