import Foundation
import Capacitor
import FirebaseCore

@objc public class GetOptionsResult: NSObject, Result {
    let options: FirebaseOptions?

    init(options: FirebaseOptions?) {
        self.options = options
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["apiKey"] = options?.apiKey ?? ""
        result["applicationId"] = options?.googleAppID ?? ""
        result["databaseUrl"] = options?.databaseURL ?? ""
        result["gcmSenderId"] = options?.gcmSenderID ?? ""
        result["projectId"] = options?.projectID ?? ""
        result["storageBucket"] = options?.storageBucket ?? ""
        return result as AnyObject
    }
}
