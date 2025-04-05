'use strict';

var core = require('@capacitor/core');
var remoteConfig = require('firebase/remote-config');

/**
 * @since 1.3.0
 */
exports.GetValueSource = void 0;
(function (GetValueSource) {
    /**
     * Indicates that the value returned is the static default value.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Static"] = 0] = "Static";
    /**
     * Indicates that the value returned was retrieved from the defaults set by the client.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Default"] = 1] = "Default";
    /**
     * Indicates that the value returned was retrieved from the Firebase Remote Config Server.
     *
     * @since 1.3.0
     */
    GetValueSource[GetValueSource["Remote"] = 2] = "Remote";
})(exports.GetValueSource || (exports.GetValueSource = {}));

const FirebaseRemoteConfig = core.registerPlugin('FirebaseRemoteConfig', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseRemoteConfigWeb()),
});

class FirebaseRemoteConfigWeb extends core.WebPlugin {
    async activate() {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        await remoteConfig.activate(remoteConfig$1);
    }
    async fetchAndActivate() {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        await remoteConfig.fetchAndActivate(remoteConfig$1);
    }
    async fetchConfig() {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        await remoteConfig.fetchConfig(remoteConfig$1);
    }
    async getBoolean(options) {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        const value = remoteConfig.getBoolean(remoteConfig$1, options.key);
        return { value };
    }
    async getNumber(options) {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        const value = remoteConfig.getNumber(remoteConfig$1, options.key);
        return { value };
    }
    async getString(options) {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        const value = remoteConfig.getString(remoteConfig$1, options.key);
        return { value };
    }
    async setMinimumFetchInterval(options) {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        remoteConfig$1.settings.minimumFetchIntervalMillis =
            options.minimumFetchIntervalInSeconds * 1000;
    }
    async setSettings(options) {
        const remoteConfig$1 = remoteConfig.getRemoteConfig();
        if (options.fetchTimeoutInSeconds !== undefined) {
            remoteConfig$1.settings.fetchTimeoutMillis =
                options.fetchTimeoutInSeconds * 1000;
        }
        if (options.minimumFetchIntervalInSeconds !== undefined) {
            remoteConfig$1.settings.minimumFetchIntervalMillis =
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseRemoteConfigWeb: FirebaseRemoteConfigWeb
});

exports.FirebaseRemoteConfig = FirebaseRemoteConfig;
//# sourceMappingURL=plugin.cjs.js.map
