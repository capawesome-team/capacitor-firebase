import Foundation
import Capacitor

@objc public class SetSettingsOptions: NSObject {
    let fetchTimeoutInSeconds: Double
    let minimumFetchIntervalInSeconds: Double

    init(_ call: CAPPluginCall) {
        self.fetchTimeoutInSeconds = SetSettingsOptions.getFetchTimeoutInSecondsFromCall(call)
        self.minimumFetchIntervalInSeconds = SetSettingsOptions.getMinimumFetchIntervalInSecondsFromCall(call)
    }

    private static func getFetchTimeoutInSecondsFromCall(_ call: CAPPluginCall) -> Double {
        return call.getDouble("fetchTimeoutInSeconds") ?? 60
    }

    private static func getMinimumFetchIntervalInSecondsFromCall(_ call: CAPPluginCall) -> Double {
        return call.getDouble("minimumFetchIntervalInSeconds") ?? 43200
    }
}
