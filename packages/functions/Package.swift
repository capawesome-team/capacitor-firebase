// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseFunctions",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebaseFunctions",
            targets: ["FirebaseFunctionsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.7.0"))
    ],
    targets: [
        .target(
            name: "FirebaseFunctionsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebaseFunctions", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseFunctionsPluginTests",
            dependencies: ["FirebaseFunctionsPlugin"],
            path: "ios/PluginTests")
    ]
)
