import Foundation
import Capacitor

@objc public class UnsubscribeFromTopicOptions: NSObject {
    let topic: String

    init(_ call: CAPPluginCall) throws {
        self.topic = try UnsubscribeFromTopicOptions.getTopicFromCall(call)
    }

    private static func getTopicFromCall(_ call: CAPPluginCall) throws -> String {
        guard let topic = call.getString("topic") else {
            throw CustomError.topicMissing
        }
        return topic
    }
}
