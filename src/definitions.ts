export interface LidtaCapacitorBlPrinterPlugin {
  /**
   * Print base64 string
   */
  printBase64(options: { msg: string, align: number }): Promise<{ value: boolean }>;
  connect(options: { address: string }): Promise<{ value: boolean }>;
  disconnect(): Promise<{ value: boolean }>;
}
