import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebasePerformancePlugin)
public class FirebasePerformancePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebasePerformancePlugin"
    public let jsName = "FirebasePerformance"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAttributes", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "incrementMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "putAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "putMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "record", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startTrace", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopTrace", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebasePerformance"
    private var implementation: FirebasePerformance?

    override public func load() {
        implementation = FirebasePerformance()
    }

    @objc func getAttribute(_ call: CAPPluginCall) {
        do {
            let options = try GetAttributeOptions(call)

            try implementation?.getAttribute(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getAttributes(_ call: CAPPluginCall) {
        do {
            let options = try GetAttributesOptions(call)

            try implementation?.getAttributes(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getMetric(_ call: CAPPluginCall) {
        do {
            let options = try GetMetricOptions(call)

            try implementation?.getMetric(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func incrementMetric(_ call: CAPPluginCall) {
        do {
            let options = try IncrementMetricOptions(call)

            try implementation?.incrementMetric(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        implementation?.isEnabled(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func putAttribute(_ call: CAPPluginCall) {
        do {
            let options = try PutAttributeOptions(call)

            try implementation?.putAttribute(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func putMetric(_ call: CAPPluginCall) {
        do {
            let options = try PutMetricOptions(call)

            try implementation?.putMetric(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func record(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func removeAttribute(_ call: CAPPluginCall) {
        do {
            let options = try RemoveAttributeOptions(call)

            try implementation?.removeAttribute(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetEnabledOptions(call)

            implementation?.setEnabled(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func startTrace(_ call: CAPPluginCall) {
        do {
            let options = try StartTraceOptions(call)

            try implementation?.startTrace(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func stopTrace(_ call: CAPPluginCall) {
        do {
            let options = try StopTraceOptions(call)

            try implementation?.stopTrace(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
