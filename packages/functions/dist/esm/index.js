import { registerPlugin } from '@capacitor/core';
const FirebaseFunctions = registerPlugin('FirebaseFunctions', {
    web: () => import('./web').then(m => new m.FirebaseFunctionsWeb()),
});
export * from './definitions';
export { FirebaseFunctions };
//# sourceMappingURL=index.js.map