import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
export class FirebaseAppWeb extends WebPlugin {
    async getName() {
        const app = getApp();
        return {
            name: app.name,
        };
    }
    async getOptions() {
        const app = getApp();
        return {
            apiKey: app.options.apiKey || '',
            applicationId: app.options.appId || '',
            databaseUrl: app.options.databaseURL || '',
            gcmSenderId: app.options.messagingSenderId || '',
            projectId: app.options.projectId || '',
            storageBucket: app.options.storageBucket || '',
        };
    }
}
//# sourceMappingURL=web.js.map