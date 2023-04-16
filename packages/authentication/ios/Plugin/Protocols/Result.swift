import Foundation
import Capacitor

@objc protocol Result {
    @objc func toJSObject() -> AnyObject
}
