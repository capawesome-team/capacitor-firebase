import { WebPlugin } from '@capacitor/core';
import type { FirebaseAppPlugin, GetNameResult, GetOptionsResult } from './definitions';
export declare class FirebaseAppWeb extends WebPlugin implements FirebaseAppPlugin {
    getName(): Promise<GetNameResult>;
    getOptions(): Promise<GetOptionsResult>;
}
