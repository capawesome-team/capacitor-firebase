import Foundation
import Capacitor

@objc public class FetchConfigOptions: NSObject {
    let minimumFetchIntervalInSeconds: Double

    init(_ call: CAPPluginCall) {
        self.minimumFetchIntervalInSeconds = FetchConfigOptions.getMinimumFetchIntervalInSecondsFromCall(call)
    }

    private static func getMinimumFetchIntervalInSecondsFromCall(_ call: CAPPluginCall) -> Double {
        return call.getDouble("minimumFetchIntervalInSeconds") ?? 43200
    }
}
