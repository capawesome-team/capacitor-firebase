import Foundation

@objc public class GetMetadataOptions: NSObject {
    private var path: String

    init(path: String) {
        self.path = path
    }

    func getPath() -> String {
        return path
    }
}
