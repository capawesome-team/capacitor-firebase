import Foundation
import Capacitor

@objc public class EnablePersistenceOptions: NSObject {
    let appName: String?
    let cacheSizeBytes: NSNumber?

    init(_ call: CAPPluginCall) {
        self.cacheSizeBytes = EnablePersistenceOptions.getCacheSizeBytesFromCall(call)
        self.appName = EnablePersistenceOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getCacheSizeBytesFromCall(_ call: CAPPluginCall) -> NSNumber? {
        return call.getInt("cacheSizeBytes") as NSNumber?
    }
}
