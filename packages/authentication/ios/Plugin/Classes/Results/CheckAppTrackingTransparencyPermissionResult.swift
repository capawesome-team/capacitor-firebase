import Capacitor
import AppTrackingTransparency

@objc public class CheckAppTrackingTransparencyPermissionResult: NSObject, Result {
    let status: ATTrackingManager.AuthorizationStatus

    init(_ status: ATTrackingManager.AuthorizationStatus) {
        self.status = status
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["status"] = convertStatusToPermissionStatus()
        return result as AnyObject
    }

    private func convertStatusToPermissionStatus() -> String {
        switch status {
        case .notDetermined:
            return "prompt"
        case .denied:
            return "denied"
        case .authorized:
            return "granted"
        case .restricted:
            return "prompt-with-rationale"
        @unknown default:
            return "prompt"
        }
    }
}
