export interface LidtaCapacitorBlPrinterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
