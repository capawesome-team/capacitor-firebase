import Foundation
import Capacitor
import FirebaseCore
import FirebaseCrashlytics

@objc public class FirebaseCrashlytics: NSObject {
    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func crash(_ message: String) {
        fatalError(message)
    }

    @objc func setCustomKey(_ key: String, _ type: String, _ call: CAPPluginCall) {
        switch type {
        case "string":
            Crashlytics.crashlytics().setCustomValue(call.getString("value") as Any, forKey: key)
        case "int":
            Crashlytics.crashlytics().setCustomValue(call.getInt("value") as Any, forKey: key)
        case "boolean":
            Crashlytics.crashlytics().setCustomValue(call.getBool("value") as Any, forKey: key)
        case "long":
            Crashlytics.crashlytics().setCustomValue(call.getInt("value") as Any, forKey: key)
        case "double":
            Crashlytics.crashlytics().setCustomValue(call.getDouble("value") as Any, forKey: key)
        case "float":
            Crashlytics.crashlytics().setCustomValue(call.getFloat("value") as Any, forKey: key)

        default:
            Crashlytics.crashlytics().setCustomValue(call.getString("value") as Any, forKey: key)
        }
    }

    @objc func log(_ message: String) {
        Crashlytics.crashlytics().log(message)
    }

    @objc func setUserID(_ userId: String) {
        Crashlytics.crashlytics().setUserID(userId)
    }

    @objc func setEnabled(_ enabled: Bool) {
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(enabled)
    }

    @objc func isEnabled() -> Bool {
        return Crashlytics.crashlytics().isCrashlyticsCollectionEnabled()
    }

    @objc func didCrashOnPreviousExecution() -> Bool {
        return Crashlytics.crashlytics().didCrashDuringPreviousExecution()
    }

    @objc func sendUnsentReports() {
        Crashlytics.crashlytics().sendUnsentReports()
    }

    @objc func deleteUnsentReports() {
        Crashlytics.crashlytics().deleteUnsentReports()
    }

    @objc func recordException(_ message: String, _ domain: String, _ code: Int) {
        let userInfo = [NSLocalizedDescriptionKey: message]
        let error = NSError(domain: domain, code: code, userInfo: userInfo)
        Crashlytics.crashlytics().record(error: error)
    }

    func recordExceptionWithStacktrace(_ message: String, _ stacktrace: [JSObject]) {
        let name = "Uncaught JavaScript exception"
        let error = ExceptionModel(name: name, reason: message)

        var customFrames: [StackFrame] = []
        for stackFrame in stacktrace {
            let functionName = (stackFrame["functionName"] as? String) ?? "(anonymous function)"
            let fileName = (stackFrame["fileName"] as? String) ?? "(unknown file)"
            let line = (stackFrame["lineNumber"] as? Int) ?? -1

            let customFrame = StackFrame(symbol: functionName, file: fileName, line: line)
            customFrames.append(customFrame)
        }
        error.stackTrace = customFrames

        Crashlytics.crashlytics().record(exceptionModel: error)
    }
}
