// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseApp",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebaseApp",
            targets: ["FirebaseAppPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.0.0"))
    ],
    targets: [
        .target(
            name: "FirebaseAppPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseAppPluginTests",
            dependencies: ["FirebaseAppPlugin"],
            path: "ios/PluginTests")
    ]
)
