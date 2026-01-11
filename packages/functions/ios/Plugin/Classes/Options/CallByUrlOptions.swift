import Foundation

@objc public class CallByUrlOptions: CallOptions {
    private var url: URL

    init(url: String, data: Any?, timeout: Int?) {
        self.url = URL(string: url)!
        super.init(data: data, timeout: timeout)
    }

    func getUrl() -> URL {
        return url
    }
}
