import Foundation
import Capacitor

@objc public class GetDownloadUrlResult: NSObject, Result {
    private var url: URL

    init(url: URL) {
        self.url = url
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["downloadUrl"] = url.absoluteString
        return result as AnyObject
    }
}
