#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseAppPlugin, "FirebaseApp",
           CAP_PLUGIN_METHOD(getName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getOptions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(initializeApp, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getApps, CAPPluginReturnPromise);
)
