// swift-tools-version: 6.1
import PackageDescription

let package = Package(
    name: "CapacitorFirebaseAuthentication",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorFirebaseAuthentication",
            targets: ["FirebaseAuthenticationPlugin"])
    ],
    traits: [
        .default(enabledTraits: ["Google", "Facebook"]),
        .trait(
            name: "Lite",
            description: "Excludes all optional third party SDKs."
        ),
        .trait(
            name: "Google",
            description: "Includes the Google Sign-In SDK."
        ),
        .trait(
            name: "Facebook",
            description: "Includes the Facebook SDK."
        )
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "12.7.0")),
        .package(url: "https://github.com/google/GoogleSignIn-iOS", from: "9.0.0"),
        .package(url: "https://github.com/facebook/facebook-ios-sdk.git", from: "18.0.0")
    ],
    targets: [
        .target(
            name: "FirebaseAuthenticationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseAuth", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "GoogleSignIn", package: "GoogleSignIn-iOS",
                         condition: .when(traits: ["Google"])),
                .product(name: "FacebookCore", package: "facebook-ios-sdk",
                         condition: .when(traits: ["Facebook"])),
                .product(name: "FacebookLogin", package: "facebook-ios-sdk",
                         condition: .when(traits: ["Facebook"]))
            ],
            path: "ios/Plugin",
            swiftSettings: [
                .define("RGCFA_INCLUDE_GOOGLE", .when(traits: ["Google"])),
                .define("RGCFA_INCLUDE_FACEBOOK", .when(traits: ["Facebook"]))
            ])
    ],
    swiftLanguageModes: [.v5]
)
