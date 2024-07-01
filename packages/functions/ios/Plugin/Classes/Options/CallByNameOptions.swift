import Foundation

@objc public class CallByNameOptions: CallOptions {
    private var name: String
    private var region: String?

    init(name: String, region: String?, data: Any?) {
        self.name = name
        self.region = region
        super.init(data: data)
    }

    func getName() -> String {
        return name
    }

    func getRegion() -> String? {
        return region
    }
}
