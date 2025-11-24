// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorFirebasePerformance",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapgoCapacitorFirebasePerformance",
            targets: ["CapgoFirebasePerformancePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "12.6.0"))
    ],
    targets: [
        .target(
            name: "CapgoFirebasePerformancePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebasePerformance", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "CapgoFirebasePerformancePluginTests",
            dependencies: ["CapgoFirebasePerformancePlugin"],
            path: "ios/PluginTests")
    ]
)
