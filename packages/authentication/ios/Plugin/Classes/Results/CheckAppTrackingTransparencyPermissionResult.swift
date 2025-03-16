import Capacitor
import AppTrackingTransparency

@objc public class CheckAppTrackingTransparencyPermissionResult: NSObject, Result {
    let status: ATTrackingManager.AuthorizationStatus

    init(_ status: ATTrackingManager.AuthorizationStatus) {
        self.status = status
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["status"] = convertStatusToPermissionStatus(status)
        return result as AnyObject
    }

    private func convertStatusToPermissionStatus(_ status: ATTrackingManager.AuthorizationStatus) -> String {
        switch status {
        case .notDetermined:
            return "prompt"
        case .denied:
            return "denied"
        case .authorized:
            return "granted"
        case .restricted:
            return "restricted"
        @unknown default:
            return "prompt"
        }
    }
}
