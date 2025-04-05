'use strict';

var core = require('@capacitor/core');

const FirebaseCrashlytics = core.registerPlugin('FirebaseCrashlytics', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseCrashlyticsWeb()),
});

class FirebaseCrashlyticsWeb extends core.WebPlugin {
    async crash() {
        throw this.unimplemented('Not implemented on web.');
    }
    async setCustomKey(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async setUserId(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async log(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async setEnabled(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async isEnabled() {
        throw this.unimplemented('Not implemented on web.');
    }
    async didCrashOnPreviousExecution() {
        throw this.unimplemented('Not implemented on web.');
    }
    async sendUnsentReports() {
        throw this.unimplemented('Not implemented on web.');
    }
    async deleteUnsentReports() {
        throw this.unimplemented('Not implemented on web.');
    }
    async recordException(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseCrashlyticsWeb: FirebaseCrashlyticsWeb
});

exports.FirebaseCrashlytics = FirebaseCrashlytics;
//# sourceMappingURL=plugin.cjs.js.map
