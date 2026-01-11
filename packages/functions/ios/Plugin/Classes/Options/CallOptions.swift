import Foundation

@objc public class CallOptions: NSObject {
    private var data: Any?
    private var timeout: Int?

    init(data: Any?, timeout: Int?) {
        self.data = data
        self.timeout = timeout
    }

    func getData() -> Any? {
        return data
    }

    func getTimeout() -> Int? {
        return timeout
    }
}
