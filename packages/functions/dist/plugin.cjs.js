'use strict';

var core = require('@capacitor/core');
var functions = require('firebase/functions');

const FirebaseFunctions = core.registerPlugin('FirebaseFunctions', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseFunctionsWeb()),
});

class FirebaseFunctionsWeb extends core.WebPlugin {
    async callByName(options) {
        const functions$1 = functions.getFunctions(undefined, options.region);
        const callable = functions.httpsCallable(functions$1, options.name);
        const result = await callable(options.data);
        return {
            data: result.data,
        };
    }
    async callByUrl(options) {
        const functions$1 = functions.getFunctions();
        const callable = functions.httpsCallableFromURL(functions$1, options.url);
        const result = await callable(options.data);
        return {
            data: result.data,
        };
    }
    async useEmulator(options) {
        const functions$1 = functions.getFunctions(undefined, options.regionOrCustomDomain);
        const port = options.port || 5001;
        functions.connectFunctionsEmulator(functions$1, options.host, port);
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseFunctionsWeb: FirebaseFunctionsWeb
});

exports.FirebaseFunctions = FirebaseFunctions;
//# sourceMappingURL=plugin.cjs.js.map
