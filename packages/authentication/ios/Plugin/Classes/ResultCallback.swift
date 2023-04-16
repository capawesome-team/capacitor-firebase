import Foundation

@objc class ResultCallback: NSObject {
    let success: (Result) -> Void
    let error: (Error) -> Void
    
    init(success: @escaping (Result) -> Void, error: @escaping (Error) -> Void) {
        self.success = success
        self.error = error
    }
}
