#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseStoragePlugin, "FirebaseStorage",
           CAP_PLUGIN_METHOD(deleteFile, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDownloadUrl, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(listFiles, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(updateMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(uploadFile, CAPPluginReturnCallback);
           CAP_PLUGIN_METHOD(useEmulator, CAPPluginReturnPromise);
)
