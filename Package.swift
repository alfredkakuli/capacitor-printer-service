// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "LidtaCapacitorBlPrinter",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "LidtaCapacitorBlPrinter",
            targets: ["LidtaCapacitorBlPrinterPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "LidtaCapacitorBlPrinterPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/LidtaCapacitorBlPrinterPlugin"),
        .testTarget(
            name: "LidtaCapacitorBlPrinterPluginTests",
            dependencies: ["LidtaCapacitorBlPrinterPlugin"],
            path: "ios/Tests/LidtaCapacitorBlPrinterPluginTests")
    ]
)