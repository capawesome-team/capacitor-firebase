#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseRemoteConfigPlugin, "FirebaseRemoteConfig",
           CAP_PLUGIN_METHOD(activate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(fetchAndActivate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(fetchConfig, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getBoolean, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getNumber, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getString, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setMinimumFetchInterval, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addConfigUpdateListener, CAPPluginReturnCallback);
           CAP_PLUGIN_METHOD(removeConfigUpdateListener, CAPPluginReturnPromise);
)
