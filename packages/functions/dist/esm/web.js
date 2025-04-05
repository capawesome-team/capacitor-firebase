import { WebPlugin } from '@capacitor/core';
import { connectFunctionsEmulator, getFunctions, httpsCallable, httpsCallableFromURL, } from 'firebase/functions';
export class FirebaseFunctionsWeb extends WebPlugin {
    async callByName(options) {
        const functions = getFunctions(undefined, options.region);
        const callable = httpsCallable(functions, options.name);
        const result = await callable(options.data);
        return {
            data: result.data,
        };
    }
    async callByUrl(options) {
        const functions = getFunctions();
        const callable = httpsCallableFromURL(functions, options.url);
        const result = await callable(options.data);
        return {
            data: result.data,
        };
    }
    async useEmulator(options) {
        const functions = getFunctions(undefined, options.regionOrCustomDomain);
        const port = options.port || 5001;
        connectFunctionsEmulator(functions, options.host, port);
    }
}
//# sourceMappingURL=web.js.map