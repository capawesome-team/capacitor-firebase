import Foundation
import Capacitor

@objc public protocol Result {
    @objc func toJSObject() -> AnyObject
}
