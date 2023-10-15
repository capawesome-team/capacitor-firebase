import Foundation

@objc public class ListFilesOptions: NSObject {
    private var path: String
    private var maxResults: Int64
    private var pageToken: String?

    init(path: String, maxResults: Int, pageToken: String?) {
        self.path = path
        self.maxResults = Int64(maxResults)
        self.pageToken = pageToken
    }

    func getPath() -> String {
        return path
    }

    func getMaxResults() -> Int64 {
        return maxResults
    }

    func getPageToken() -> String? {
        return pageToken
    }
}
