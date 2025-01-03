package com.lidta.plugins.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.getcapacitor.JSArray;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.util.Xml.Encoding;
import android.util.Base64;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

@CapacitorPlugin(name = "LidtaCapacitorBlPrinter")
public class LidtaCapacitorBlPrinterPlugin extends Plugin {

    private LidtaCapacitorBlPrinter implementation = new LidtaCapacitorBlPrinter();

private static final String LOG_TAG = "BluetoothPrinter";
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    Bitmap bitmap;
    String encoding = "iso-8859-1";

    public static final byte LINE_FEED = 0x0A;
    public static final byte[] CODIFICATION = new byte[] { 0x1b, 0x4D, 0x01 };

    public static final byte[] ESC_ALIGN_LEFT = { 0x1B, 0x61, 0x00 };
    public static final byte[] ESC_ALIGN_RIGHT = { 0x1B, 0x61, 0x02 };
    public static final byte[] ESC_ALIGN_CENTER = { 0x1B, 0x61, 0x01 };

    public static final byte[] CHAR_SIZE_00 = { 0x1B, 0x21, 0x00 };// Normal size
    public static final byte[] CHAR_SIZE_01 = { 0x1B, 0x21, 0x01 };// Reduzided width
    public static final byte[] CHAR_SIZE_08 = { 0x1B, 0x21, 0x08 };// bold normal size
    public static final byte[] CHAR_SIZE_10 = { 0x1B, 0x21, 0x10 };// Double height size
    public static final byte[] CHAR_SIZE_11 = { 0x1B, 0x21, 0x11 };// Reduzided Double height size
    public static final byte[] CHAR_SIZE_20 = { 0x1B, 0x21, 0x20 };// Double width size
    public static final byte[] CHAR_SIZE_30 = { 0x1B, 0x21, 0x30 };
    public static final byte[] CHAR_SIZE_31 = { 0x1B, 0x21, 0x31 };
    public static final byte[] CHAR_SIZE_51 = { 0x1B, 0x21, 0x51 };
    public static final byte[] CHAR_SIZE_61 = { 0x1B, 0x21, 0x61 };

    public static final byte[] UNDERL_OFF = { 0x1b, 0x2d, 0x00 }; // Underline font OFF
    public static final byte[] UNDERL_ON = { 0x1b, 0x2d, 0x01 }; // Underline font 1-dot ON
    public static final byte[] UNDERL2_ON = { 0x1b, 0x2d, 0x02 }; // Underline font 2-dot ON
    public static final byte[] BOLD_OFF = { 0x1b, 0x45, 0x00 }; // Bold font OFF
    public static final byte[] BOLD_ON = { 0x1b, 0x45, 0x01 }; // Bold font ON
    public static final byte[] FONT_A = { 0x1b, 0x4d, 0x00 }; // Font type A
    public static final byte[] FONT_B = { 0x1b, 0x4d, 0x01 }; // Font type B

    public static final byte[] BARCODE_UPC_A = { 0x1D, 0x6B, 0x00 };
    public static final byte[] BARCODE_UPC_E = { 0x1D, 0x6B, 0x01 };
    public static final byte[] BARCODE_EAN13 = { 0x1D, 0x6B, 0x02 };
    public static final byte[] BARCODE_EAN8 = { 0x1D, 0x6B, 0x03 };
    public static final byte[] BARCODE_CODE39 = { 0x1D, 0x6B, 0x04 };
    public static final byte[] BARCODE_ITF = { 0x1D, 0x6B, 0x05 };
    public static final byte[] BARCODE_CODABAR = { 0x1D, 0x6B, 0x06 };
    public static final int REQUEST_BLUETOOTH_PERMISSION = 1;


    @PluginMethod
    public void connect(PluginCall call) {
        String deviceAddress = call.getString("address");
        if (deviceAddress == null || deviceAddress.isEmpty()) {
            call.reject("Device address is required");
            return;
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            call.reject("Bluetooth is not supported on this device");
            return;
        }

        try {
            BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard Serial Port Profile UUID
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mBluetoothAdapter.cancelDiscovery();
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            call.resolve();
        } catch (IOException e) {
            call.reject("Connection failed: " + e.getMessage());
        }
    }



    @PluginMethod
    public void disconnect(PluginCall call) {
        if (mmSocket != null) {
            try {
                mmSocket.close();
                mmSocket = null;
                mmOutputStream = null;
                call.resolve();
            } catch (IOException e) {
                call.reject("Disconnection failed: " + e.getMessage());
            }
        } else {
            call.resolve(); // Already disconnected
        }
    }


    @PluginMethod
    public void getPairedDevices(PluginCall call) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            call.reject("Bluetooth is not supported on this device");
            return;
        }

        Set<BluetoothDevice> mmDevices = mBluetoothAdapter.getBondedDevices();
        JSArray devicesArray = new JSArray(); // Use JSArray here

        if (mmDevices.size() > 0) {
            for (BluetoothDevice device : mmDevices) {
                JSObject deviceObject = new JSObject();
                deviceObject.put("name", device.getName() != null ? device.getName() : "Unknown"); // Handle null names
                deviceObject.put("address", device.getAddress());
                devicesArray.put(deviceObject); // Add to JSArray
            }
        }

        JSObject ret = new JSObject();
        ret.put("devices", devicesArray); // Put the JSArray into the main JSObject
        call.resolve(ret);
    }



    @PluginMethod
    public boolean printBase64(PluginCall call) {
        try {

            final String encodedString = call.getString("msg");
            final int align = call.getInt("align");
            final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
            final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            bitmap = decodedBitmap;
            int mWidth = bitmap.getWidth();
            int mHeight = bitmap.getHeight();

            bitmap = resizeImage(bitmap, 48 * 12, mHeight);

            byte[] bt = decodeBitmapBase64(bitmap);

            // not work
            Log.d(LOG_TAG, "SWITCH ALIGN BASE64 -> " + align);
            switch (align) {
                case 0:
                    mmOutputStream.write(ESC_ALIGN_LEFT);
                    mmOutputStream.write(bt);
                    break;
                case 1:
                    mmOutputStream.write(ESC_ALIGN_CENTER);
                    mmOutputStream.write(bt);
                    break;
                case 2:
                    mmOutputStream.write(ESC_ALIGN_RIGHT);
                    mmOutputStream.write(bt);
                    break;
                default:
                    call.reject("Invlaid align value");
                    break;
            }

            // tell the user data were sent

            Log.d(LOG_TAG, "PRINT BASE64 SEND");
            call.resolve();
            return true;

        } catch (Exception e) {
            String errMsg = e.getMessage();
            Log.e(LOG_TAG, errMsg);
            e.printStackTrace();
            call.reject(errMsg);
            return false;
        }
    }


    private static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();

        if (width > w) {
            float scaleWidth = ((float) w) / width;
            float scaleHeight = ((float) h) / height + 24;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            Bitmap resizedBitmap = Bitmap.createBitmap(w, height + 24, Config.RGB_565);
            Canvas canvas = new Canvas(resizedBitmap);
            Paint paint = new Paint();
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, (w - width) / 2, 0, paint);
            return resizedBitmap;
        }
    }

    public static byte[] decodeBitmapBase64(Bitmap bmp) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        List<String> list = new ArrayList<String>(); // binaryString list
        StringBuffer sb;
        int bitLen = bmpWidth / 8;
        int zeroCount = bmpWidth % 8;
        String zeroStr = "";
        if (zeroCount > 0) {
            bitLen = bmpWidth / 8 + 1;
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < bmpHeight; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < bmpWidth; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160) {
                    sb.append("0");
                } else {
                    sb.append("1");
                }
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binaryListToHexStringList(list);
        String commandHexString = "1D763000";

        // construct xL and xH
        // there are 8 pixels per byte. In case of modulo: add 1 to compensate.
        bmpWidth = bmpWidth % 8 == 0 ? bmpWidth / 8 : (bmpWidth / 8 + 1);
        int xL = bmpWidth % 256;
        int xH = (bmpWidth - xL) / 256;

        String xLHex = Integer.toHexString(xL);
        String xHHex = Integer.toHexString(xH);
        if (xLHex.length() == 1) {
            xLHex = "0" + xLHex;
        }
        if (xHHex.length() == 1) {
            xHHex = "0" + xHHex;
        }
        String widthHexString = xLHex + xHHex;

        // construct yL and yH
        int yL = bmpHeight % 256;
        int yH = (bmpHeight - yL) / 256;

        String yLHex = Integer.toHexString(yL);
        String yHHex = Integer.toHexString(yH);
        if (yLHex.length() == 1) {
            yLHex = "0" + yLHex;
        }
        if (yHHex.length() == 1) {
            yHHex = "0" + yHHex;
        }
        String heightHexString = yLHex + yHHex;

        List<String> commandList = new ArrayList<String>();
        commandList.add(commandHexString + widthHexString + heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    public static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<byte[]>();

        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        byte[] bytes = sysCopy(commandList);
        return bytes;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static List<String> binaryListToHexStringList(List<String> list) {
        List<String> hexList = new ArrayList<String>();
        for (String binaryStr : list) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < binaryStr.length(); i += 8) {
                String str = binaryStr.substring(i, i + 8);

                String hexString = myBinaryStrToHexString(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;

    }

    public static String myBinaryStrToHexString(String binaryStr) {
        String hex = "";
        String f4 = binaryStr.substring(0, 4);
        String b4 = binaryStr.substring(4, 8);
        for (int i = 0; i < binaryArray.length; i++) {
            if (f4.equals(binaryArray[i])) {
                hex += hexStr.substring(i, i + 1);
            }
        }
        for (int i = 0; i < binaryArray.length; i++) {
            if (b4.equals(binaryArray[i])) {
                hex += hexStr.substring(i, i + 1);
            }
        }

        return hex;
    }

    private static String hexStr = "0123456789ABCDEF";

    private static String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
            "1001", "1010", "1011", "1100", "1101", "1110", "1111" };



    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
