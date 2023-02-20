#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseMessagingPlugin, "FirebaseMessaging",
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isSupported, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getToken, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(deleteToken, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDeliveredNotifications, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeDeliveredNotifications, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAllDeliveredNotifications, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAllListeners, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(subscribeToTopic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(unsubscribeFromTopic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(createChannel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(deleteChannel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(listChannels, CAPPluginReturnPromise);
)
