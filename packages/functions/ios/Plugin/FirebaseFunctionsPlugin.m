#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseFunctionsPlugin, "FirebaseFunctions",
           CAP_PLUGIN_METHOD(callByName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(callByUrl, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(useEmulator, CAPPluginReturnPromise);
)
