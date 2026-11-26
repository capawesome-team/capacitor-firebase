import Foundation

@objc public class CallByUrlOptions: CallOptions {
    private var url: URL

    init?(url: String, data: Any?, timeout: Int?) {
        guard let url = URL(string: url) else {
            return nil
        }
        self.url = url
        super.init(data: data, timeout: timeout)
    }

    func getUrl() -> URL {
        return url
    }
}
