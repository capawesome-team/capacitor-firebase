import Foundation
import FirebaseCore
import FirebaseAppCheck

public class CustomAppCheckProviderFactory: NSObject, AppCheckProviderFactory {
    public func createProvider(with app: FirebaseApp) -> AppCheckProvider? {
        return AppAttestProvider(app: app)
    }
}
