import { WebPlugin } from '@capacitor/core';
import type { AppCheck } from 'firebase/app-check';
import type { FirebaseAppCheckPlugin, GetTokenOptions, GetTokenResult, InitializeOptions, SetTokenAutoRefreshEnabledOptions } from './definitions';
declare global {
    interface Window {
        FIREBASE_APPCHECK_DEBUG_TOKEN: boolean | string;
    }
}
export declare class FirebaseAppCheckWeb extends WebPlugin implements FirebaseAppCheckPlugin {
    static readonly tokenChangedEvent = "tokenChanged";
    static readonly errorNotInitialized = "AppCheck has not been initialized.";
    static readonly errorSiteKeyMissing = "siteKey must be provided.";
    private _appCheckInstance;
    get appCheckInstance(): AppCheck | undefined;
    set appCheckInstance(value: AppCheck | undefined);
    private onTokenChangedListenerUnsubscribe;
    getToken(options?: GetTokenOptions): Promise<GetTokenResult>;
    initialize(options?: InitializeOptions): Promise<void>;
    setTokenAutoRefreshEnabled(options: SetTokenAutoRefreshEnabledOptions): Promise<void>;
    private registerOnTokenChangedListener;
    private unregisterOnTokenChangedListener;
    private handleTokenChanged;
}
