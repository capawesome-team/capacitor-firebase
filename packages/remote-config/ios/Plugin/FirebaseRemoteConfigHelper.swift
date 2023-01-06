import Foundation
import FirebaseRemoteConfig

public class FirebaseRemoteConfigHelper {
    public static func mapRemoteConfigSourceToInt(_ source: RemoteConfigSource) -> Int {
        if source == .static {
            return 0
        } else if source == .remote {
            return 2
        } else {
            return 1
        }
    }
}
