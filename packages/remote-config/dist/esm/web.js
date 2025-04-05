import { WebPlugin } from '@capacitor/core';
import { activate, fetchAndActivate, fetchConfig, getBoolean, getNumber, getRemoteConfig, getString, } from 'firebase/remote-config';
export class FirebaseRemoteConfigWeb extends WebPlugin {
    async activate() {
        const remoteConfig = getRemoteConfig();
        await activate(remoteConfig);
    }
    async fetchAndActivate() {
        const remoteConfig = getRemoteConfig();
        await fetchAndActivate(remoteConfig);
    }
    async fetchConfig() {
        const remoteConfig = getRemoteConfig();
        await fetchConfig(remoteConfig);
    }
    async getBoolean(options) {
        const remoteConfig = getRemoteConfig();
        const value = getBoolean(remoteConfig, options.key);
        return { value };
    }
    async getNumber(options) {
        const remoteConfig = getRemoteConfig();
        const value = getNumber(remoteConfig, options.key);
        return { value };
    }
    async getString(options) {
        const remoteConfig = getRemoteConfig();
        const value = getString(remoteConfig, options.key);
        return { value };
    }
    async setMinimumFetchInterval(options) {
        const remoteConfig = getRemoteConfig();
        remoteConfig.settings.minimumFetchIntervalMillis =
            options.minimumFetchIntervalInSeconds * 1000;
    }
    async setSettings(options) {
        const remoteConfig = getRemoteConfig();
        if (options.fetchTimeoutInSeconds !== undefined) {
            remoteConfig.settings.fetchTimeoutMillis =
                options.fetchTimeoutInSeconds * 1000;
        }
        if (options.minimumFetchIntervalInSeconds !== undefined) {
            remoteConfig.settings.minimumFetchIntervalMillis =
                options.minimumFetchIntervalInSeconds * 1000;
        }
    }
    async addConfigUpdateListener(_callback) {
        this.throwUnimplementedError();
    }
    async removeConfigUpdateListener(_options) {
        this.throwUnimplementedError();
    }
    throwUnimplementedError() {
        throw this.unimplemented('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map