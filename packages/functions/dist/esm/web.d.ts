import { WebPlugin } from '@capacitor/core';
import type { CallByNameOptions, CallByNameResult, CallByUrlOptions, CallResult, FirebaseFunctionsPlugin, UseEmulatorOptions } from './definitions';
export declare class FirebaseFunctionsWeb extends WebPlugin implements FirebaseFunctionsPlugin {
    callByName<RequestData = unknown, ResponseData = unknown>(options: CallByNameOptions<RequestData>): Promise<CallByNameResult<ResponseData>>;
    callByUrl<RequestData = unknown, ResponseData = unknown>(options: CallByUrlOptions<RequestData>): Promise<CallResult<ResponseData>>;
    useEmulator(options: UseEmulatorOptions): Promise<void>;
}
