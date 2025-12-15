// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseFirestore",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorFirebaseFirestore",
            targets: ["FirebaseFirestorePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "11.7.0"))
    ],
    targets: [
        .target(
            name: "FirebaseFirestorePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebaseFirestore", package: "firebase-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FirebaseFirestorePluginTests",
            dependencies: ["FirebaseFirestorePlugin"],
            path: "ios/PluginTests")
    ]
)
