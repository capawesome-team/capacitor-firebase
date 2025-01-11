// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseRemoteConfig",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorFirebaseRemoteConfig",
            targets: ["FirebaseRemoteConfigPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.0"))
    ],
    targets: [
        .target(
            name: "FirebaseRemoteConfigPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "Firebase"),
                .product(name: "FirebaseRemoteConfig", package: "Firebase")
            ],
            path: "ios/Sources/FirebaseRemoteConfigPlugin"),
        .testTarget(
            name: "FirebaseRemoteConfigPluginTests",
            dependencies: ["FirebaseRemoteConfigPlugin"],
            path: "ios/Tests/FirebaseRemoteConfigPluginTests")
    ]
)
