// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebasePerformance",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebasePerformance",
            targets: ["FirebasePerformancePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.7.0"))
    ],
    targets: [
        .target(
            name: "FirebasePerformancePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebasePerformance", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebasePerformancePluginTests",
            dependencies: ["FirebasePerformancePlugin"],
            path: "ios/PluginTests")
    ]
)
