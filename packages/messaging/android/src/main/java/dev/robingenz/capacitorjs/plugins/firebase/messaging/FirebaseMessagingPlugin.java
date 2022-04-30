package dev.robingenz.capacitorjs.plugins.firebase.messaging;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseMessaging", permissions = @Permission(strings = {}, alias = "receive"))
public class FirebaseMessagingPlugin extends Plugin {}
