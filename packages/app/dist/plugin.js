var capacitorFirebaseApp = (function (exports, core, app) {
    'use strict';

    const FirebaseApp = core.registerPlugin('FirebaseApp', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseAppWeb()),
    });

    class FirebaseAppWeb extends core.WebPlugin {
        async getName() {
            const app$1 = app.getApp();
            return {
                name: app$1.name,
            };
        }
        async getOptions() {
            const app$1 = app.getApp();
            return {
                apiKey: app$1.options.apiKey || '',
                applicationId: app$1.options.appId || '',
                databaseUrl: app$1.options.databaseURL || '',
                gcmSenderId: app$1.options.messagingSenderId || '',
                projectId: app$1.options.projectId || '',
                storageBucket: app$1.options.storageBucket || '',
            };
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FirebaseAppWeb: FirebaseAppWeb
    });

    exports.FirebaseApp = FirebaseApp;

    return exports;

})({}, capacitorExports, firebaseAppExports);
//# sourceMappingURL=plugin.js.map
