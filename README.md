# lidta-capacitor-bl-printer

Print directly to bl printer
Currently only available for android devices

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
[Visit Documentation Website](https://app.lidta.com/plugins/capacitor)

## Vue 3 Capacitor 6 Capacitor exmaple 
[See Vue 3 Capacitor 6 Example here](https://github.com/alfredkakuli/lidta-capacitor-bl-printer-example)


### Useful Links
[![Official Website](https://www.npmjs.com/npm-avatar/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdmF0YXJVUkwiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci84MmMyYjhiZjY2MjFkZWM2MDQzMmRhMGZkM2EzY2M1ND9zaXplPTUwJmRlZmF1bHQ9cmV0cm8ifQ.HRDfqcUyaVF684N6a0RoyeP8odjHx9UbIBA_k8Uo8XM)](https://app.lidta.com) 



This project does amazing things, and I’m glad you’re here!

## Support the Project

If you like this project and want to support its development, feel free to buy me a coffee:

[![Buy me a coffee](https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png)](https://buymeacoffee.com/alfredkakuli)

Your support is greatly appreciated and will help me keep improving this project.