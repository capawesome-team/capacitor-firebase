import Foundation

@objc public class CallOptions: NSObject {
    private var data: Any?

    init(data: Any?) {
        self.data = data
    }

    func getData() -> Any? {
        return data
    }
}
