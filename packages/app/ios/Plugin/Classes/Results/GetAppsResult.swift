import Foundation
import Capacitor
import FirebaseCore

@objc public class GetAppsResult: NSObject, Result {
    let apps: [FirebaseCore.FirebaseApp]

    init(apps: [FirebaseCore.FirebaseApp]) {
        self.apps = apps
    }

    @objc public func toJSObject() -> AnyObject {
        var appsResult = JSArray()
        for app in apps {
            var appResult = JSObject()
            appResult["name"] = app.name
            appsResult.append(appResult)
        }
        var result = JSObject()
        result["apps"] = appsResult
        return result as AnyObject
    }
}
