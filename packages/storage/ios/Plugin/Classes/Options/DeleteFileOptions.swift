import Foundation

@objc public class DeleteFileOptions: NSObject {
    private var path: String

    init(path: String) {
        self.path = path
    }

    func getPath() -> String {
        return path
    }
}
