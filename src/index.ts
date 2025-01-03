import { registerPlugin } from '@capacitor/core';

import type { LidtaCapacitorBlPrinterPlugin } from './definitions';

const LidtaCapacitorBlPrinter = registerPlugin<LidtaCapacitorBlPrinterPlugin>('LidtaCapacitorBlPrinter', {
  web: () => import('./web').then((m) => new m.LidtaCapacitorBlPrinterWeb()),
});

export * from './definitions';
export { LidtaCapacitorBlPrinter };
