// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseAppCheck",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorFirebaseAppCheck",
            targets: ["FirebaseAppCheckPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "12.7.0"))
    ],
    targets: [
        .target(
            name: "FirebaseAppCheckPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseAppCheck", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseAppCheckPluginTests",
            dependencies: ["FirebaseAppCheckPlugin"],
            path: "ios/PluginTests")
    ]
)
