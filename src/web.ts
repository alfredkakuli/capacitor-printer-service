import { WebPlugin } from '@capacitor/core';

import type { LidtaCapacitorBlPrinterPlugin } from './definitions';

export class LidtaCapacitorBlPrinterWeb extends WebPlugin implements LidtaCapacitorBlPrinterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
