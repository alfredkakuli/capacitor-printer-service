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

* [`echo(...)`](#echo)
* [`printBase64(...)`](#printbase64)
* [`connect(...)`](#connect)
* [`disconnect()`](#disconnect)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

Echoes back the value that was passed into it

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


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

## Documentation
[Visit Documentation Website](https://app.lidta.com/plugins/capacitor)

## Vue 3 Capacitor exmaple 
[See Example here](https://github.com/alfredkakuli/lidta-capacitor-bl-printer-example)


### Useful Links
[![Official Website](https://app.lidta.com/public/logo.png)](https://app.lidta.com)



This project does amazing things, and I’m glad you’re here!

## Support the Project

If you like this project and want to support its development, feel free to buy me a coffee:

[![Buy me a coffee](https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png)](https://buymeacoffee.com/alfredkakuli)

Your support is greatly appreciated and will help me keep improving this project.

</docgen-api>
