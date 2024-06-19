import Foundation

@objc public class CallByUrlOptions: CallOptions {
    private var url: URL

    init(url: String, data: Any?) {
        self.url = URL(string: url)!
        super.init(data: data)
    }

    func getUrl() -> URL {
        return url
    }
}
