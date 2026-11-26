import Foundation
import Capacitor

@objc public class EnablePersistenceOptions: NSObject {
    let cacheSizeBytes: NSNumber?

    init(_ call: CAPPluginCall) {
        self.cacheSizeBytes = EnablePersistenceOptions.getCacheSizeBytesFromCall(call)
    }

    private static func getCacheSizeBytesFromCall(_ call: CAPPluginCall) -> NSNumber? {
        return call.getInt("cacheSizeBytes") as NSNumber?
    }
}
