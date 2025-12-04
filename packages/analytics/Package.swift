// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorFirebaseAnalytics",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapgoCapacitorFirebaseAnalytics",
            targets: ["CapgoFirebaseAnalyticsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "12.6.0"))
    ],
    targets: [
        .target(
            name: "CapgoFirebaseAnalyticsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseAnalytics", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "CapgoFirebaseAnalyticsPluginTests",
            dependencies: ["CapgoFirebaseAnalyticsPlugin"],
            path: "ios/PluginTests")
    ]
)
