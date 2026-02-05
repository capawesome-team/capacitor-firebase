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
        let data = options.getData()
        let timeout = options.getTimeout()

        let functions = getFunctions(options.getRegion())
        let callable = functions.httpsCallable(name)
        if let timeout = timeout {
            callable.timeoutInterval = TimeInterval(timeout) / 1000.0
        }
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
        let timeout = options.getTimeout()

        let functions = getFunctions(nil)
        let callable = functions.httpsCallable(url)
        if let timeout = timeout {
            callable.timeoutInterval = TimeInterval(timeout) / 1000.0
        }
        callable.call(data) { (result, error) in
            if let error = error {
                completion(nil, error)
            } else {
                let result = CallResult(data: result?.data)
                completion(result, nil)
            }
        }
    }

    @objc func useEmulator(_ host: String, _ port: Int, _ regionOrCustomDomain: String?) {
        let functions = getFunctions(regionOrCustomDomain)
        functions.useEmulator(withHost: host, port: port)
    }

    private func getFunctions(_ regionOrCustomDomain: String?) -> Functions {
        guard let value = regionOrCustomDomain else {
            return Functions.functions()
        }
        if value.hasPrefix("http://") || value.hasPrefix("https://") {
            return Functions.functions(customDomain: value)
        }
        return Functions.functions(region: value)
    }
}
