// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseAnalytics",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebaseAnalytics",
            targets: ["FirebaseAnalyticsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.7.0"))
    ],
    targets: [
        .target(
            name: "FirebaseAnalyticsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseAnalytics", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseAnalyticsPluginTests",
            dependencies: ["FirebaseAnalyticsPlugin"],
            path: "ios/PluginTests")
    ]
)
