import { WebPlugin } from '@capacitor/core';

import type { LidtaCapacitorBlPrinterPlugin } from './definitions';

export class LidtaCapacitorBlPrinterWeb extends WebPlugin implements LidtaCapacitorBlPrinterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async printBase64(options: { msg: string, align: number }): Promise<{ value: boolean }> {
    console.log('printBase64', options);
    return { value: true };
  }
  async connect(options: { address: string }): Promise<{ value: boolean }> {
    console.log('connect', options);
    return { value: true };
  }
  async disconnect(): Promise<{ value: boolean }> {
    console.log('disconnect');
    return { value: true };
  }
}
