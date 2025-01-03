import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LidtaCapacitorBlPrinterPlugin)
public class LidtaCapacitorBlPrinterPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "LidtaCapacitorBlPrinterPlugin"
    public let jsName = "LidtaCapacitorBlPrinter"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = LidtaCapacitorBlPrinter()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
