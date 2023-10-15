import Foundation

@objc public class UploadFileOptions: NSObject {
    private var path: String
    private var uri: URL
    private var callbackId: String

    init(path: String, uri: URL, callbackId: String) {
        self.path = path
        self.uri = uri
        self.callbackId = callbackId
    }

    func getPath() -> String {
        return path
    }

    func getUri() -> URL {
        return uri
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
