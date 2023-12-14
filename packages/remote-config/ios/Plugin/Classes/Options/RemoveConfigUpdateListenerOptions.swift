import Foundation

@objc public class RemoveConfigUpdateListenerOptions: NSObject {
    private var callbackId: String

    init(callbackId: String) {
        self.callbackId = callbackId
    }

    func getCallbackId() -> String {
        return self.callbackId
    }
}
