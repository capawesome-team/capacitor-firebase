// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseCrashlytics",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorFirebaseCrashlytics",
            targets: ["FirebaseCrashlyticsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "12.6.0"))
    ],
    targets: [
        .target(
            name: "FirebaseCrashlyticsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCrashlytics", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseCrashlyticsPluginTests",
            dependencies: ["FirebaseCrashlyticsPlugin"],
            path: "ios/PluginTests")
    ]
)
