import Foundation
import Capacitor
import FirebaseCore
import FirebaseCrashlytics

@objc public class FirebaseCrashlytics: NSObject {
    override init() {
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseCrashlytics] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func crash(_ options: CrashOptions) {
        fatalError(options.message)
    }

    @objc public func deleteUnsentReports(completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().deleteUnsentReports()
        completion(nil)
    }

    @objc public func didCrashOnPreviousExecution(completion: @escaping (_ result: DidCrashOnPreviousExecutionResult?, _ error: Error?) -> Void) {
        let crashed = Crashlytics.crashlytics().didCrashDuringPreviousExecution()
        let result = DidCrashOnPreviousExecutionResult(crashed: crashed)
        completion(result, nil)
    }

    @objc public func isEnabled(completion: @escaping (_ result: IsEnabledResult?, _ error: Error?) -> Void) {
        let enabled = Crashlytics.crashlytics().isCrashlyticsCollectionEnabled()
        let result = IsEnabledResult(enabled: enabled)
        completion(result, nil)
    }

    @objc public func log(_ options: LogOptions, completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().log(options.message)
        completion(nil)
    }

    @objc public func recordException(_ options: RecordExceptionOptions, completion: @escaping (_ error: Error?) -> Void) {
        if let stacktrace = options.stacktrace, !stacktrace.isEmpty {
            recordExceptionModel(options)
        } else {
            recordError(options)
        }
        completion(nil)
    }

    @objc public func sendUnsentReports(completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().sendUnsentReports()
        completion(nil)
    }

    @objc public func setCustomKey(_ options: SetCustomKeyOptions, completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().setCustomValue(options.value as Any, forKey: options.key)
        completion(nil)
    }

    @objc public func setEnabled(_ options: SetEnabledOptions, completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(options.enabled)
        completion(nil)
    }

    @objc public func setUserId(_ options: SetUserIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        Crashlytics.crashlytics().setUserID(options.userId)
        completion(nil)
    }

    private func recordError(_ options: RecordExceptionOptions) {
        let userInfo = [NSLocalizedDescriptionKey: options.message]
        let error = NSError(domain: options.domain, code: options.code, userInfo: userInfo)

        var additionalInformation: [String: Any] = [:]
        for keyAndValue in options.keysAndValues ?? [] {
            guard let key = keyAndValue[keyPath: "key"] as? String, let value = keyAndValue[keyPath: "value"] else {
                continue
            }
            additionalInformation[key] = value
        }

        Crashlytics.crashlytics().record(error: error, userInfo: additionalInformation)
    }

    private func recordExceptionModel(_ options: RecordExceptionOptions) {
        let name = "Uncaught JavaScript exception"
        let error = ExceptionModel(name: name, reason: options.message)

        var customFrames: [StackFrame] = []
        for stackFrame in options.stacktrace ?? [] {
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
