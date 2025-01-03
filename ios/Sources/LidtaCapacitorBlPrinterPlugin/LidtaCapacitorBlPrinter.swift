import Foundation

@objc public class LidtaCapacitorBlPrinter: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
