import { registerPlugin } from '@capacitor/core';
const FirebasePerformance = registerPlugin('FirebasePerformance', {
    web: () => import('./web').then(m => new m.FirebasePerformanceWeb()),
});
export * from './definitions';
export { FirebasePerformance };
//# sourceMappingURL=index.js.map