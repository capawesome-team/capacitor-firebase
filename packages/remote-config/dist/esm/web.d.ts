import { WebPlugin } from '@capacitor/core';
import type { AddConfigUpdateListenerOptionsCallback, FirebaseRemoteConfigPlugin, GetBooleanResult, GetNumberResult, GetOptions, GetStringResult, RemoveConfigUpdateListenerOptions, SetMinimumFetchIntervalOptions, SetSettingsOptions } from './definitions';
export declare class FirebaseRemoteConfigWeb extends WebPlugin implements FirebaseRemoteConfigPlugin {
    activate(): Promise<void>;
    fetchAndActivate(): Promise<void>;
    fetchConfig(): Promise<void>;
    getBoolean(options: GetOptions): Promise<GetBooleanResult>;
    getNumber(options: GetOptions): Promise<GetNumberResult>;
    getString(options: GetOptions): Promise<GetStringResult>;
    setMinimumFetchInterval(options: SetMinimumFetchIntervalOptions): Promise<void>;
    setSettings(options: SetSettingsOptions): Promise<void>;
    addConfigUpdateListener(_callback: AddConfigUpdateListenerOptionsCallback): Promise<string>;
    removeConfigUpdateListener(_options: RemoveConfigUpdateListenerOptions): Promise<void>;
    private throwUnimplementedError;
}
