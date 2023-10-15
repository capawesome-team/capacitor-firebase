import Foundation

@objc public class GetDownloadUrlOptions: NSObject {
    private var path: String

    init(path: String) {
        self.path = path
    }

    func getPath() -> String {
        return path
    }
}
