import Foundation
import Capacitor
import FirebaseStorage

@objc public class ListFilesResult: NSObject, Result {
    private var listResult: StorageListResult

    init(result: StorageListResult) {
        self.listResult = result
    }

    public func toJSObject() -> AnyObject {
        var itemsResult = JSArray()
        for item in listResult.items {
            var itemResult = JSObject()
            itemResult["bucket"] = item.bucket
            itemResult["path"] = item.fullPath
            itemResult["name"] = item.name
            itemsResult.append(itemResult)
        }

        var result = JSObject()
        result["items"] = itemsResult
        result["nextPageToken"] = listResult.pageToken
        return result as AnyObject
    }
}
