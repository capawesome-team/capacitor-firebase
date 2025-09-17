import Foundation
import FirebaseAuth
import Capacitor

@objc class GetIdTokenResultResult: NSObject {
    let tokenResult: AuthTokenResult?

    init(tokenResult: AuthTokenResult?) {
        self.tokenResult = tokenResult
    }

    func toJSObject() -> JSObject {
        var result = JSObject()

        if let tokenResult = tokenResult {
            // Convert to milliseconds (JS commonly expects ms since epoch)
            result["authTime"] = Int(tokenResult.authDate.timeIntervalSince1970 * 1000)
            result["expirationTime"] = Int(tokenResult.expirationDate.timeIntervalSince1970 * 1000)
            result["issuedAtTime"] = Int(tokenResult.issuedAtDate.timeIntervalSince1970 * 1000)
            result["signInProvider"] = tokenResult.signInProvider
            result["signInSecondFactor"] = tokenResult.signInSecondFactor

            var claims = JSObject()
            for (key, value) in tokenResult.claims {
                if let jsonValue = value as? JSValue {
                    claims[key] = jsonValue
                }
            }
            result["claims"] = claims
        }

        return result
    }
}
