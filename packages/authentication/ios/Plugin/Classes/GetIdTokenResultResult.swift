import Foundation
import FirebaseAuth
import Capacitor

@objc class GetIdTokenResultResult: NSObject {
    let tokenResult: IdTokenResult?

    init(tokenResult: IdTokenResult?) {
        self.tokenResult = tokenResult
    }

    func toJSObject() -> JSObject {
        var result = JSObject()
        
        if let tokenResult = tokenResult {
            result["authTime"] = tokenResult.authTime
            result["expirationTime"] = tokenResult.expirationDate.timeIntervalSince1970 
            result["issuedAtTime"] = tokenResult.issuedAt.timeIntervalSince1970  
            result["signInProvider"] = tokenResult.signInProvider  
            result["signInSecondFactor"] = tokenResult.signInSecondFactor          
            
            let claims = JSObject()
            for (key, value) in tokenResult.claims {
                claims[key] = value 
            }
            result["claims"] = claims
        }
        
        return result
    }
}
