#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FirebaseAuthenticationPlugin, "FirebaseAuthentication",
           CAP_PLUGIN_METHOD(getCurrentUser, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getIdToken, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setLanguageCode, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithApple, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithFacebook, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithGithub, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithGoogle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithMicrosoft, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithPlayGames, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithTwitter, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithYahoo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithPhoneNumber, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithCustomToken, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signOut, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(useAppLanguage, CAPPluginReturnPromise);
)
