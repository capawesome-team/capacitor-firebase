#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebasePerformancePlugin, "FirebasePerformance",
           CAP_PLUGIN_METHOD(startTrace, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopTrace, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(incrementMetric, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(putAttribute, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(getAttribute, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAttributes, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAttribute, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(putMetric, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(getMetric, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(record, CAPPluginReturnNone);
)
