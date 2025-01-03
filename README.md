# lidta-capacitor-bl-printer

lidta-capacitor-bl-printer is the first free Capacitor solution to fully support print most documents including html with structure and styles to
blutooth pos thermal printers without breaking any sweat

## Currently only available for android devices

## Install

```bash
npm install lidta-capacitor-bl-printer
npx cap sync
```

## API

<docgen-index>

* [`printBase64(...)`](#printbase64)
* [`connect(...)`](#connect)
* [`disconnect()`](#disconnect)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### printBase64(...)

```typescript
printBase64(options: { msg: string; align: number; }) => Promise<{ value: boolean; }>
```

Print base64 string

| Param         | Type                                         |
| ------------- | -------------------------------------------- |
| **`options`** | <code>{ msg: string; align: number; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### connect(...)

```typescript
connect(options: { address: string; }) => Promise<{ value: boolean; }>
```

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ address: string; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### disconnect()

```typescript
disconnect() => Promise<{ value: boolean; }>
```

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------

</docgen-api>

## Documentation

[Visit Documentation Website](https://app.lidta.com/#/plugins/capacitor)

## Vue 3 Capacitor 6 Capacitor exmaple

## the following component uses vue 3 with composition API Syntax

```Js
<script setup>
import { onMounted, ref } from 'vue'; //vue life cycle hooks(onMounted) and reactive (ref)
import html2canvas from 'html2canvas'; //converts the html or any text to base64Image
import { LidtaCapacitorBlPrinter } from 'lidta-capacitor-bl-printer'; //lidta-capacitor-bl-printer plugin

const devices = ref([]);
const connectedPrinter = ref({});
const content = ref(null);

const listDevices = async () => {
  await LidtaCapacitorBlPrinter.getPairedDevices().then((result) => {
      devices.value = result.devices || [];
  }).catch((error) => {
    alert(error);
  });
}

const printReceipt = async (device) => {
 await  LidtaCapacitorBlPrinter.connect({ address: device.address }).then(async () => {
   connectedPrinter.value = device.name;
   await  html2canvas(
      content.value,
    ).then(async (canvas) => {
    const base64Image =  canvas.toDataURL("image/png");
    LidtaCapacitorBlPrinter.printBase64({msg: base64Image, align: 1, /*0=>left 1=>center, 2=>right anything else=>invalid */ 
    }).then(() => { LidtaCapacitorBlPrinter.disconnect();
   }).catch((error) => alert(error)).finally(async () => {});
  }).catch((error) => alert(error));
  }).catch((error) => alert(error))
};  
</script>
```

## The following template uses Vuetify

```html
<template>
<div class=" mt-8">
  <div  class="text-center" >
    <VBtn color="warning" class="text-center" variant="tonal"  @click="listDevices">
      <VIcon icon="mdi-printer" size="24" color="info" ></VIcon>List Connected Devices</VBtn>
  </div>
  
 <div class="text-center">
   <VBtn variant="tonal" class="mt-1" v-for="device in devices" :key="device" @click="printReceipt(device)" color="indigo">
  <VIcon icon="mdi-printer" ></VIcon>  Print on {{ device.name }} 
  </VBtn>
 </div>
  <div class="text-center">
    connected {{ connectedPrinter?.name }}
  </div>

<div  ref="content" id="content">
    <h1>Hellow World</h1>
    <img  height="80" width="80" src="@/assets/logo.png"  />
</div>
```

[Clone  Full Vue 3 Capacitor 6 Example here](https://github.com/alfredkakuli/lidta-capacitor-bl-printer-example)

### Useful Links

 Official Website  [Lidta tech](https://app.lidta.com)

## Support the Project

This is the first free solution to fully support print most documents including html with structure and styles to
blutooth pos thermal printers without breaking any sweat.

<a href="https://www.buymeacoffee.com/alfredkakuli">
  <img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy me a coffee" width="150"/>
</a>

Your support is greatly appreciated and will help me keep improving this project.