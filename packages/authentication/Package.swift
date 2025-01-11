// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseAuthentication",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebaseAuthentication",
            targets: ["FirebaseAuthenticationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.0"))
    ],
    targets: [
        .target(
            name: "FirebaseAuthenticationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseAuth", package: "Firebase"),
                .product(name: "FirebaseCore", package: "Firebase")
            ],
            path: "ios/Sources/FirebaseAuthenticationPlugin"),
        .testTarget(
            name: "FirebaseAuthenticationPluginTests",
            dependencies: ["FirebaseAuthenticationPlugin"],
            path: "ios/Tests/FirebaseAuthenticationPluginTests")
    ]
)
