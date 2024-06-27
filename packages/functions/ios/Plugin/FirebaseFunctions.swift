import Foundation
import FirebaseCore
import FirebaseFunctions

@objc public class FirebaseFunctions: NSObject {
    private let plugin: FirebaseFunctionsPlugin

    init(plugin: FirebaseFunctionsPlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func callByName(_ options: CallByNameOptions, completion: @escaping (Result?, Error?) -> Void) {
        let name = options.getName()
        let region = options.getRegion()
        let data = options.getData()

        let functions: Functions
        if let region = region {
            functions = Functions.functions(region: region)
        } else {
            functions = Functions.functions()
        }
        let callable = functions.httpsCallable(name)
        callable.call(data) { (result, error) in
            if let error = error {
                completion(nil, error)
            } else {
                let result = CallResult(data: result?.data)
                completion(result, nil)
            }
        }
    }

    @objc public func callByUrl(_ options: CallByUrlOptions, completion: @escaping (Result?, Error?) -> Void) {
        let url = options.getUrl()
        let data = options.getData()

        let functions = Functions.functions()
        let callable = functions.httpsCallable(url)
        callable.call(data) { (result, error) in
            if let error = error {
                completion(nil, error)
            } else {
                let result = CallResult(data: result?.data)
                completion(result, nil)
            }
        }
    }
    
    @objc func useEmulator(_ host: String, _ port: Int) {
        Functions.functions().useEmulator(withHost: host, port: port)
    }
}
