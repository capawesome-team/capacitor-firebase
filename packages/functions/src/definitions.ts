export interface FirebaseFunctionsPlugin {
  callByName(options: CallByNameOptions): Promise<CallByNameResult>;
  callByUrl(options: CallByUrlOptions): Promise<CallByUrlResult>;
}

export interface CallByNameOptions extends CallOptions {
  name: string;
}

export type CallByNameResult = CallResult;

export interface CallByUrlOptions extends CallOptions {
  url: string;
}

export type CallByUrlResult = CallResult;

export interface CallOptions {
  data: any;
}

export interface CallResult {
  data: any;
}
