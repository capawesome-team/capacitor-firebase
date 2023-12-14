import Foundation

@objc public class AddConfigUpdateListenerOptions: NSObject {
    private var callbackId: String

    init(callbackId: String) {
        self.callbackId = callbackId
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
