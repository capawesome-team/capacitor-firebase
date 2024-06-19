import Foundation

@objc public class CallByNameOptions: CallOptions {
    private var name: String

    init(name: String, data: Any?) {
        self.name = name
        super.init(data: data)
    }

    func getName() -> String {
        return name
    }
}
