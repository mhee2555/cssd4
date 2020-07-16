package com.phc.cssd.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

public class TSCUSBActivity extends Activity implements Runnable {
    private int TIMEOUT = 100;
    private TextView QQ;
    private static final String TAG = "DemoKit";
    public UsbManager mUsbManager;
    public UsbDevice mUsbDevice;
    private UsbInterface mUsbIntf;
    private static UsbDeviceConnection mUsbConnection;
    private static UsbEndpoint mUsbendpoint;
    private static UsbEndpoint usbEndpointIn;
    private static UsbEndpoint usbEndpointOut;
    private static PendingIntent mPermissionIntent;
    private boolean mPermissionRequestPending;
    private static UsbAccessory mAccessory;
    private static ParcelFileDescriptor mFileDescriptor;
    private static FileInputStream mInputStream;
    private static FileOutputStream mOutputStream;
    private static final int MESSAGE_SWITCH = 1;
    private static final int MESSAGE_TEMPERATURE = 2;
    private static final int MESSAGE_LIGHT = 3;
    private static final int MESSAGE_JOY = 4;
    public static final byte LED_SERVO_COMMAND = 2;
    public static final byte RELAY_COMMAND = 3;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private Button openbtn;
    private Button sendbtn;
    private Button mopenbtn;
    private Button msendbtn;
    private Button comopenbtn;
    private TextView label1;
    private EditText editext1;
    private UsbConstants test;
    private Thread readThread;
    private StringBuilder strIN = new StringBuilder();
    private int receivelength = 0;
    private int response = 0;
    private static String printerstatus = "";
    private static String receive_data = "";
    private byte[] readBuf = new byte[1024];
    private int MAX_USBFS_BUFFER_SIZE = 10000;
    private static boolean hasPermissionToCommunicate = false;
    private UsbManager manager;
    private UsbDevice device;
    private UsbDevice device2;
    IntentFilter filterAttached_and_Detached = new IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED");
    private final BroadcastReceiver mUsbReceiver_main = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.example.USB_PERMISSION".equals(action)) {
                synchronized(this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra("device");
                    if (intent.getBooleanExtra("permission", false) && device != null) {
                        com.phc.cssd.model.TSCUSBActivity.hasPermissionToCommunicate = true;
                    }
                }
            }

        }
    };
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.example.USB_PERMISSION".equals(action)) {
                synchronized(this) {
                    com.phc.cssd.model.TSCUSBActivity.this.mUsbDevice = (UsbDevice)intent.getParcelableExtra("device");
                    if (intent.getBooleanExtra("permission", false)) {
                        if (com.phc.cssd.model.TSCUSBActivity.this.mUsbDevice != null) {
                            com.phc.cssd.model.TSCUSBActivity.this.mopen();
                        }
                    } else {
                        Log.d("ERROR", "permission denied for device " + com.phc.cssd.model.TSCUSBActivity.this.device);
                    }
                }
            }

        }
    };

    public TSCUSBActivity() {
    }

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.setContentView( 2130903040 );
        this.mUsbManager = (UsbManager)this.getSystemService("usb");
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
        IntentFilter filter = new IntentFilter("com.android.example.USB_PERMISSION");
        this.registerReceiver(this.mUsbReceiver_main, filter);
        UsbAccessory[] accessoryList = this.mUsbManager.getAccessoryList();
        HashMap<String, UsbDevice> deviceList = this.mUsbManager.getDeviceList();
        Log.d("Detect ", deviceList.size() + " USB device(s) found");
        Iterator deviceIterator = deviceList.values().iterator();

        while(deviceIterator.hasNext()) {
            this.device = (UsbDevice)deviceIterator.next();
            if (this.device.getVendorId() == 4611) {
                break;
            }
        }

        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.android.example.USB_PERMISSION"), 1073741824);
        this.mUsbManager.requestPermission(this.device, mPermissionIntent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //this.getMenuInflater().inflate(2131165184, menu);
        return true;
    }

    public void run() {
        boolean mRunning = true;

        while(mRunning) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                ;
            }

            byte[] dest = new byte[64];
            int timeoutMillis = 100;
            //int readAmt = true;
            int length = mUsbConnection.bulkTransfer(usbEndpointIn, dest, dest.length, timeoutMillis);
            if (length <= 0) {
                break;
            }

            this.receivelength = length;

            for(int i = 0; i < this.receivelength - 1; ++i) {
                this.strIN.append((char)dest[i]);
            }
        }

    }

    public void threadopen() {
        Thread thread = new Thread(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                TSCUSBActivity.this.mUsbManager = (UsbManager) TSCUSBActivity.this.getSystemService("usb");
                TSCUSBActivity.this.mUsbDevice = (UsbDevice) TSCUSBActivity.this.getIntent().getParcelableExtra("device");
                TSCUSBActivity.this.mUsbIntf = TSCUSBActivity.this.mUsbDevice.getInterface(0);
                TSCUSBActivity.mUsbendpoint = TSCUSBActivity.this.mUsbIntf.getEndpoint(0);
                TSCUSBActivity.mUsbConnection = TSCUSBActivity.this.mUsbManager.openDevice(TSCUSBActivity.this.mUsbDevice);
                TSCUSBActivity.mUsbConnection.claimInterface(TSCUSBActivity.this.mUsbIntf, true);

                for(int i = 0; i < TSCUSBActivity.this.mUsbIntf.getEndpointCount(); ++i) {
                    UsbEndpoint end = TSCUSBActivity.this.mUsbIntf.getEndpoint(i);
                    if (end.getDirection() == 128) {
                        TSCUSBActivity.usbEndpointIn = end;
                    } else {
                        TSCUSBActivity.usbEndpointOut = end;
                    }
                }

                String printercommand = "FEED 100\n";
                byte[] command = printercommand.getBytes();
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
            }
        });
        thread.start();
    }

    @SuppressLint("WrongConstant")
    public void open() {
        this.mUsbManager = (UsbManager)this.getSystemService("usb");
        this.mUsbDevice = (UsbDevice)this.getIntent().getParcelableExtra("device");
        this.mUsbIntf = this.mUsbDevice.getInterface(0);
        mUsbendpoint = this.mUsbIntf.getEndpoint(0);
        mUsbConnection = this.mUsbManager.openDevice(this.mUsbDevice);
        mUsbConnection.claimInterface(this.mUsbIntf, true);

        for(int i = 0; i < this.mUsbIntf.getEndpointCount(); ++i) {
            UsbEndpoint end = this.mUsbIntf.getEndpoint(i);
            if (end.getDirection() == 128) {
                usbEndpointIn = end;
            } else {
                usbEndpointOut = end;
            }
        }

        this.label1.setText("Success");
        this.readThread = new Thread(this);
    }

    @SuppressLint("WrongConstant")
    public void mopen() {
        this.mUsbManager = (UsbManager)this.getSystemService("usb");
        this.mUsbIntf = this.device.getInterface(0);
        mUsbendpoint = this.mUsbIntf.getEndpoint(0);
        mUsbConnection = this.mUsbManager.openDevice(this.device);
        mUsbConnection.claimInterface(this.mUsbIntf, true);

        for(int i = 0; i < this.mUsbIntf.getEndpointCount(); ++i) {
            UsbEndpoint end = this.mUsbIntf.getEndpoint(i);
            if (end.getDirection() == 128) {
                usbEndpointIn = end;
            } else {
                usbEndpointOut = end;
            }
        }
        this.label1.setText("Success");
    }

    private void sendbythread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Log.d("DemoKit", "send finish. ");
                String printercommand = "FEED 100\n";
                String printercommand2 = "FEED 100\n";
                byte[] command = printercommand.getBytes();
                byte[] command2 = printercommand2.getBytes();
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command2, command2.length, TSCUSBActivity.this.TIMEOUT);
            }
        });
        thread.start();
        this.USBReceive();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException var4) {
            ;
        }

        do {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException var3) {
                ;
            }
        } while(this.response != 1);

        this.label1.setText(this.strIN);
        this.editext1.setText(Integer.toString(this.receivelength));
    }

    public void msendbythread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String printercommand = "FEED 100\n";
                String printercommand2 = "~!F\n";
                byte[] command = printercommand.getBytes();
                byte[] command2 = printercommand2.getBytes();
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command2, command2.length, TSCUSBActivity.this.TIMEOUT);
            }
        });
        thread.start();
        this.USBReceive();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException var4) {

        }

        do {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException var3) {

            }
        } while(this.response != 1);

        this.label1.setText(this.strIN);
        this.editext1.setText(Integer.toString(this.receivelength));
    }

    @SuppressLint("WrongConstant")
    public void checkInfo() {
        this.manager = (UsbManager)this.getSystemService("usb");
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
        IntentFilter filter = new IntentFilter("com.android.example.USB_PERMISSION");
        this.registerReceiver(this.mUsbReceiver, filter);
        HashMap<String, UsbDevice> deviceList = this.manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        String i = "";
        if (deviceIterator.hasNext()) {
            this.device = (UsbDevice)deviceIterator.next();
            this.manager.requestPermission(this.device, mPermissionIntent);
            i = i + "\nDeviceID: " + this.device.getDeviceId() + "\n" + "DeviceName: " + this.device.getDeviceName() + "\n" + "DeviceClass: " + this.device.getDeviceClass() + " - " + "DeviceSubClass: " + this.device.getDeviceSubclass() + "\n" + "VendorID: " + this.device.getVendorId() + "\n" + "ProductID: " + this.device.getProductId() + "\n";
        }
    }

    private String USBReceive() {
        Thread ReceiveThread = new Thread(new Runnable() {
            public void run() {
                boolean mRunning = true;
                if (mRunning) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var7) {
                        ;
                    }

                    byte[] dest = new byte[128];
                    int timeoutMillis = 100;
                    //int readAmt = true;
                    int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, dest, dest.length, timeoutMillis);
                    if (length > 0) {
                        TSCUSBActivity.this.response = 0;
                        TSCUSBActivity.this.receivelength = length;

                        for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                            TSCUSBActivity.this.strIN.append((char)dest[i]);
                        }
                    } else {
                        TSCUSBActivity.this.response = 1;
                    }
                }

            }
        });
        ReceiveThread.start();

        try {
            Thread.sleep(50L);
        } catch (InterruptedException var3) {
            ;
        }

        return this.response != 1 ? "" : this.strIN.toString();
    }

    private boolean ReadStream_judge() {
        Thread ReceiveThread = new Thread(new Runnable() {
            public void run() {
                boolean mRunning = true;

                label29:
                while(mRunning) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var7) {
                        ;
                    }

                    byte[] dest = new byte[128];
                    int timeoutMillis = 100;
                    //int readAmt = true;
                    int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, dest, dest.length, timeoutMillis);
                    if (length > 0) {
                        TSCUSBActivity.this.response = 0;
                        TSCUSBActivity.this.receivelength = length;
                        int i = 0;

                        while(true) {
                            if (i >= TSCUSBActivity.this.receivelength - 1) {
                                continue label29;
                            }

                            TSCUSBActivity.this.strIN.append((char)dest[i]);
                            ++i;
                        }
                    }

                    TSCUSBActivity.this.response = 1;
                    break;
                }

            }
        });
        ReceiveThread.start();

        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var3) {
                ;
            }
        } while(this.response != 1);

        receive_data = this.strIN.toString();
        if (receive_data.contains("ENDLINE")) {
            receive_data = receive_data.replace("ENDLINE", "");
            return true;
        } else {
            return true;
        }
    }

    private void returndata_test() {
        Thread sendthread = new Thread(new Runnable() {
            public void run() {
                Log.d("DemoKit", "send finish. ");
                String printercommand = "~!F\n";
                byte[] command = printercommand.getBytes();
                TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
            }
        });
        sendthread.start();

        try {
            Thread.sleep(300L);
        } catch (InterruptedException var5) {
            ;
        }

        Thread recvhread = new Thread(new Runnable() {
            public void run() {
                boolean mRunning = true;

                label29:
                while(mRunning) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var7) {

                    }

                    byte[] dest = new byte[64];
                    int timeoutMillis = 100;
                    //int readAmt = true;
                    int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, dest, dest.length, timeoutMillis);
                    if (length > 0) {
                        TSCUSBActivity.this.response = 0;
                        TSCUSBActivity.this.receivelength = length;
                        int i = 0;

                        while(true) {
                            if (i >= TSCUSBActivity.this.receivelength - 1) {
                                continue label29;
                            }

                            TSCUSBActivity.this.strIN.append((char)dest[i]);
                            ++i;
                        }
                    }

                    TSCUSBActivity.this.response = 1;
                    break;
                }

            }
        });
        recvhread.start();

        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }
        } while(this.response != 1);

        this.label1.setText(this.strIN);
        this.editext1.setText(Integer.toString(this.receivelength));
    }

    public String openport(UsbManager manager, UsbDevice device) {
        this.mUsbIntf = device.getInterface(0);
        mUsbendpoint = this.mUsbIntf.getEndpoint(0);
        mUsbConnection = manager.openDevice(device);
        boolean port_status = mUsbConnection.claimInterface(this.mUsbIntf, true);

        for(int i = 0; i < this.mUsbIntf.getEndpointCount(); ++i) {
            UsbEndpoint end = this.mUsbIntf.getEndpoint(i);
            if (end.getDirection() == 128) {
                usbEndpointIn = end;
            } else {
                usbEndpointOut = end;
            }
        }

        if (port_status) {
            return "1";
        } else {
            return "-1";
        }
    }

    public String openport(UsbManager manager, UsbDevice device, int delay) {
        this.mUsbIntf = device.getInterface(0);
        mUsbendpoint = this.mUsbIntf.getEndpoint(0);
        mUsbConnection = manager.openDevice(device);
        boolean port_status = mUsbConnection.claimInterface(this.mUsbIntf, true);

        for(int i = 0; i < this.mUsbIntf.getEndpointCount(); ++i) {
            UsbEndpoint end = this.mUsbIntf.getEndpoint(i);
            if (end.getDirection() == 128) {
                usbEndpointIn = end;
            } else {
                usbEndpointOut = end;
            }
        }

        try {
            Thread.sleep((long)delay);
        } catch (InterruptedException var7) {
            var7.printStackTrace();
        }

        if (port_status) {
            return "1";
        } else {
            return "-1";
        }
    }

    public String sendcommand(final String printercommand) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    byte[] command = printercommand.getBytes();
                    TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "-1";
        }
    }

    public String sendcommandUTF8(final String message) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    byte[] msgBuffer = null;

                    try {
                        msgBuffer = message.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException var3) {
                        var3.printStackTrace();
                    }

                    TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, msgBuffer, msgBuffer.length, TSCUSBActivity.this.TIMEOUT);
                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "-1";
        }
    }

    public String sendcommandBig5(final String message) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    byte[] msgBuffer = null;

                    try {
                        msgBuffer = message.getBytes("big5");
                    } catch (UnsupportedEncodingException var3) {
                        var3.printStackTrace();
                    }

                    TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, msgBuffer, msgBuffer.length, TSCUSBActivity.this.TIMEOUT);
                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "-1";
        }
    }

    public String sendcommandGB2312(final String message) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    byte[] msgBuffer = null;

                    try {
                        msgBuffer = message.getBytes("GB2312");
                    } catch (UnsupportedEncodingException var3) {
                        var3.printStackTrace();
                    }

                    TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, msgBuffer, msgBuffer.length, TSCUSBActivity.this.TIMEOUT);
                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "-1";
        }
    }

    public String sendcommand(final byte[] command) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, command.length, TSCUSBActivity.this.TIMEOUT);
                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "1";
        }
    }

    public String sendcommand_largebyte(final byte[] command) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    int length = command.length;
                    //int remain_datax = false;

                    for(int i = 0; i <= length; i += TSCUSBActivity.this.MAX_USBFS_BUFFER_SIZE) {
                        int remain_data = length - i;
                        if (remain_data < TSCUSBActivity.this.MAX_USBFS_BUFFER_SIZE && i != 0) {
                            TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, i, remain_data, TSCUSBActivity.this.TIMEOUT);
                        } else {
                            TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.mUsbendpoint, command, i, TSCUSBActivity.this.MAX_USBFS_BUFFER_SIZE, TSCUSBActivity.this.TIMEOUT);
                        }
                    }

                }
            });
            thread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                ;
            }

            return "1";
        }
    }

    public String setup(int width, int height, int speed, int density, int sensor, int sensor_distance, int sensor_offset) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            String size = "SIZE " + width + " mm" + ", " + height + " mm";
            String speed_value = "SPEED " + speed;
            String density_value = "DENSITY " + density;
            String sensor_value = "";
            if (sensor == 0) {
                sensor_value = "GAP " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
            } else if (sensor == 1) {
                sensor_value = "BLINE " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
            }

            message = size + "\r\n" + speed_value + "\r\n" + density_value + "\r\n" + sensor_value + "\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String clearbuffer() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "CLS\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String barcode(int x, int y, String type, int height, int human_readable, int rotation, int narrow, int wide, String string) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            String barcode = "BARCODE ";
            String position = x + "," + y;
            String mode = "\"" + type + "\"";
            String height_value = "" + height;
            String human_value = "" + human_readable;
            String rota = "" + rotation;
            String narrow_value = "" + narrow;
            String wide_value = "" + wide;
            String string_value = "\"" + string + "\"";
            message = barcode + position + " ," + mode + " ," + height_value + " ," + human_value + " ," + rota + " ," + narrow_value + " ," + wide_value + " ," + string_value + "\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String qrcode(int x, int y, String ecc, String cell, String mode, String rotation, String model, String mask, String content) {
        String message = "QRCODE " + x + "," + y + "," + ecc + "," + cell + "," + mode + "," + rotation + "," + model + "," + mask + "," + "\"" + content + "\"" + "\r\n";
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String bar(String x, String y, String width, String height) {
        String message = "BAR " + x + "," + y + "," + width + "," + height + "\r\n";
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String printerfont(int x, int y, String size, int rotation, int x_multiplication, int y_multiplication, String string) throws UnsupportedEncodingException {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            String text = "TEXT ";
            String position = x + "," + y;
            String size_value = "\"" + size + "\"";
            String rota = "" + rotation;
            String x_value = "" + x_multiplication;
            String y_value = "" + y_multiplication;
            String string_value = "\"" + string + "\"";
            message = text + position + " ," + size_value + " ," + rota + " ," + x_value + " ," + y_value + " ," + string_value + "\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String printlabel(int quantity, int copy) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            message = "PRINT " + quantity + ", " + copy + "\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String formfeed() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            message = "FORMFEED\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String nobackfeed() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "";
            message = "SET TEAR OFF\r\n";
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);
            return "1";
        }
    }

    public String sendfile(String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream("/sdcard/Download/" + filename);
                byte[] data = new byte[fis.available()];

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(data);
                fis.close();
            } catch (Exception var4) {
                ;
            }

            return "1";
        }
    }

    public String sendfile(String path, String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream("/sdcard" + path + filename);
                byte[] data = new byte[fis.available()];

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(data);
                fis.close();
            } catch (Exception var5) {
                ;
            }

            return "1";
        }
    }

    public byte[] return_file_byte(String path, String filename) {
        byte[] error_status = "ERROR".getBytes();
        byte[] data = new byte[1024];

        try {
            FileInputStream fis = new FileInputStream("/sdcard" + path + filename);
            data = new byte[fis.available()];
            fis.close();
            return data;
        } catch (Exception var6) {
            return error_status;
        }
    }

    public String downloadfile(String path, String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream(path + filename);
                byte[] data = new byte[fis.available()];
                String download = "DOWNLOAD F,\"" + filename + "\"," + data.length + ",";
                byte[] download_head = download.getBytes();

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(download_head);
                this.sendcommand(data);
                fis.close();
            } catch (Exception var8) {
                ;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            return "1";
        }
    }

    public String downloadfile(String path, String filename, String savename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream(path + filename);
                byte[] data = new byte[fis.available()];
                String download = "DOWNLOAD F,\"" + savename + "\"," + data.length + ",";
                byte[] download_head = download.getBytes();

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(download_head);
                this.sendcommand(data);
                fis.close();
            } catch (Exception var9) {
                ;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var8) {
                var8.printStackTrace();
            }

            return "1";
        }
    }

    public String downloadpcx(String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream("/sdcard/Download/" + filename);
                byte[] data = new byte[fis.available()];
                String download = "DOWNLOAD F,\"" + filename + "\"," + data.length + ",";
                byte[] download_head = download.getBytes();

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(download_head);
                this.sendcommand(data);
                fis.close();
            } catch (Exception var7) {
                ;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            return "1";
        }
    }

    public String downloadbmp(String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream("/sdcard/Download/" + filename);
                byte[] data = new byte[fis.available()];
                String download = "DOWNLOAD F,\"" + filename + "\"," + data.length + ",";
                byte[] download_head = download.getBytes();

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(download_head);
                this.sendcommand(data);
                fis.close();
            } catch (Exception var7) {
                ;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            return "1";
        }
    }

    public String downloadttf(String filename) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                FileInputStream fis = new FileInputStream("/sdcard/Download/" + filename);
                byte[] data = new byte[fis.available()];
                String download = "DOWNLOAD F,\"" + filename + "\"," + data.length + ",";
                byte[] download_head = download.getBytes();

                while(fis.read(data) != -1) {
                    ;
                }

                this.sendcommand(download_head);
                fis.close();
            } catch (Exception var7) {
                ;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            return "1";
        }
    }

    public String closeport() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                Thread.sleep(1300L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }

            try {
                mUsbConnection.close();
                mUsbConnection.releaseInterface(this.mUsbIntf);
                mUsbConnection = null;
                mUsbendpoint = null;
                this.mUsbManager = null;
                this.mUsbDevice = null;
                this.mUsbIntf = null;
                Log.d("DemoKit", "Device closed. ");
            } catch (Exception var2) {
                Log.e("DemoKit", "Exception: " + var2.getMessage());
            }

            return "1";
        }
    }

    public String closeport(int timeout) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            try {
                Thread.sleep((long)timeout);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            try {
                mUsbConnection.close();
                mUsbConnection.releaseInterface(this.mUsbIntf);
                mUsbConnection = null;
                mUsbendpoint = null;
                this.mUsbManager = null;
                this.mUsbDevice = null;
                this.mUsbIntf = null;
                Log.d("DemoKit", "Device closed. ");
            } catch (Exception var3) {
                Log.e("DemoKit", "Exception: " + var3.getMessage());
            }

            return "1";
        }
    }

    public String printername() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!T";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            String name = this.USBReceive();
            return name;
        }
    }

    public String printername(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!T";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            String name = this.USBReceive();
            return name;
        }
    }

    public String printermemory() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!A";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            String memory = this.USBReceive();
            return memory;
        }
    }

    public String printermemory(int dlelay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!A";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep((long)dlelay);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            String memory = this.USBReceive();
            return memory;
        }
    }

    public String printermileage() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!@";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            String mileage = this.USBReceive();
            return mileage;
        }
    }

    public String printermileage(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!@";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            String mileage = this.USBReceive();
            return mileage;
        }
    }

    public String sendcommand_getstring(String message) {
        this.strIN = new StringBuilder();
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String end_judge = "OUT \"ENDLINE\"\r\n";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            byte[] msgBuffer1 = "\r\n".getBytes();
            byte[] msgBuffer2 = end_judge.getBytes();
            this.sendcommand(msgBuffer);
            this.sendcommand(msgBuffer1);
            this.sendcommand(msgBuffer2);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            try {
                while(!this.ReadStream_judge()) {
                    ;
                }
            } catch (Exception var8) {
                return "-1";
            }

            return receive_data;
        }
    }

    public String printercodepage() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!I";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            String codepage = this.USBReceive();
            return codepage;
        }
    }

    public String printercodepage(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!I";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            String codepage = this.USBReceive();
            return codepage;
        }
    }

    public String printerfile() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!F";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            String files = this.USBReceive();
            return files;
        }
    }

    public String printerfile(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String message = "~!F";
            this.readBuf = new byte[1024];
            byte[] msgBuffer = message.getBytes();
            this.sendcommand(msgBuffer);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            String files = this.USBReceive();
            return files;
        }
    }

    public String restart() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 82};
            this.sendcommand(message);
            return "1";
        }
    }

    public byte printerstatus_byte() {
        if (mUsbConnection == null) {
            return -1;
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var6) {

                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length <= 0) {
                            TSCUSBActivity.this.response = 1;
                            TSCUSBActivity.this.readBuf[0] = -1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var6) {

            }

            return this.readBuf[0];
        }
    }

    public byte printerstatus_byte(int delay) {
        if (mUsbConnection == null) {
            return -1;
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);
            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var6) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 1000;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                        } else {
                            TSCUSBActivity.this.response = 1;
                            TSCUSBActivity.this.readBuf[0] = -1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            return this.readBuf[0];
        }
    }

    public String printerstatus() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var6) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var6) {
                ;
            }

            if (this.readBuf[0] == 0) {
                query = "00";
            } else if (this.readBuf[0] == 1) {
                query = "01";
            } else if (this.readBuf[0] == 2) {
                query = "02";
            } else if (this.readBuf[0] == 3) {
                query = "03";
            } else if (this.readBuf[0] == 4) {
                query = "04";
            } else if (this.readBuf[0] == 5) {
                query = "05";
            } else if (this.readBuf[0] == 8) {
                query = "08";
            } else if (this.readBuf[0] == 9) {
                query = "09";
            } else if (this.readBuf[0] == 10) {
                query = "0A";
            } else if (this.readBuf[0] == 11) {
                query = "0B";
            } else if (this.readBuf[0] == 12) {
                query = "0C";
            } else if (this.readBuf[0] == 13) {
                query = "0D";
            } else if (this.readBuf[0] == 16) {
                query = "10";
            } else if (this.readBuf[0] == 32) {
                query = "20";
            } else if (this.readBuf[0] == 128) {
                query = "80";
            }

            return query;
        }
    }

    public String printerstatus(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);
            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var6) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 300;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            if (this.readBuf[0] == 0) {
                query = "00";
            } else if (this.readBuf[0] == 1) {
                query = "01";
            } else if (this.readBuf[0] == 2) {
                query = "02";
            } else if (this.readBuf[0] == 3) {
                query = "03";
            } else if (this.readBuf[0] == 4) {
                query = "04";
            } else if (this.readBuf[0] == 5) {
                query = "05";
            } else if (this.readBuf[0] == 8) {
                query = "08";
            } else if (this.readBuf[0] == 9) {
                query = "09";
            } else if (this.readBuf[0] == 10) {
                query = "0A";
            } else if (this.readBuf[0] == 11) {
                query = "0B";
            } else if (this.readBuf[0] == 12) {
                query = "0C";
            } else if (this.readBuf[0] == 13) {
                query = "0D";
            } else if (this.readBuf[0] == 16) {
                query = "10";
            } else if (this.readBuf[0] == 32) {
                query = "20";
            } else if (this.readBuf[0] == 128) {
                query = "80";
            }

            return query;
        }
    }

    public String queryprinter() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var6) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();
            if (this.readBuf[0] == 0) {
                query = "0";
            } else if (this.readBuf[0] == 1) {
                query = "1";
            } else if (this.readBuf[0] == 2) {
                query = "2";
            } else if (this.readBuf[0] == 3) {
                query = "3";
            } else if (this.readBuf[0] == 4) {
                query = "4";
            } else if (this.readBuf[0] == 5) {
                query = "5";
            } else if (this.readBuf[0] == 8) {
                query = "8";
            } else if (this.readBuf[0] == 9) {
                query = "9";
            } else if (this.readBuf[0] == 10) {
                query = "A";
            } else if (this.readBuf[0] == 11) {
                query = "B";
            } else if (this.readBuf[0] == 12) {
                query = "C";
            } else if (this.readBuf[0] == 13) {
                query = "D";
            } else if (this.readBuf[0] == 16) {
                query = "10";
            } else if (this.readBuf[0] == 32) {
                query = "20";
            } else if (this.readBuf[0] == 128) {
                query = "80";
            }

            return query;
        }
    }

    public String queryprinter(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 63};
            //int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var6) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();
            if (this.readBuf[0] == 0) {
                query = "0";
            } else if (this.readBuf[0] == 1) {
                query = "1";
            } else if (this.readBuf[0] == 2) {
                query = "2";
            } else if (this.readBuf[0] == 3) {
                query = "3";
            } else if (this.readBuf[0] == 4) {
                query = "4";
            } else if (this.readBuf[0] == 5) {
                query = "5";
            } else if (this.readBuf[0] == 8) {
                query = "8";
            } else if (this.readBuf[0] == 9) {
                query = "9";
            } else if (this.readBuf[0] == 10) {
                query = "A";
            } else if (this.readBuf[0] == 11) {
                query = "B";
            } else if (this.readBuf[0] == 12) {
                query = "C";
            } else if (this.readBuf[0] == 13) {
                query = "D";
            } else if (this.readBuf[0] == 16) {
                query = "10";
            } else if (this.readBuf[0] == 32) {
                query = "20";
            } else if (this.readBuf[0] == 128) {
                query = "80";
            }

            return query;
        }
    }

    public String status() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            //int length = false;
            String query = "";
            byte[] message = new byte[]{27, 33, 83};
            this.readBuf = new byte[1024];
            this.strIN = new StringBuilder();
            this.sendcommand(message);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var7) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                            TSCUSBActivity.this.receivelength = length;

                            for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                                TSCUSBActivity.this.strIN.append((char) TSCUSBActivity.this.readBuf[i]);
                            }
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep(300L);
            } catch (InterruptedException var6) {
                ;
            }

            if (this.readBuf[0] == 2 && this.readBuf[5] == 3) {
                for(int tim = 0; tim <= 7; ++tim) {
                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ready";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Head Open";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Head Open";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 72 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ribbon Jam";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 68 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ribbon Empty";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "No Paper";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 66 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Paper Jam";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Paper Empty";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 67 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Cutting";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 75 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Waiting to Press Print Key";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 76 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Waiting to Take Label";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 80 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Printing Batch";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 96 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Pause";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Pause";
                        this.readBuf = new byte[1024];
                        break;
                    }
                }

                return printerstatus;
            } else {
                return "";
            }
        }
    }

    public String status(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            // int length = false;
            String query = "";
            byte[] message = new byte[]{27, 33, 83};
            this.readBuf = new byte[1024];
            this.strIN = new StringBuilder();
            this.sendcommand(message);

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var8) {
                var8.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var7) {

                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                       //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                            TSCUSBActivity.this.receivelength = length;

                            for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                                TSCUSBActivity.this.strIN.append((char) TSCUSBActivity.this.readBuf[i]);
                            }
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep(300L);
            } catch (InterruptedException var7) {
                ;
            }

            if (this.readBuf[0] == 2 && this.readBuf[5] == 3) {
                for(int tim = 0; tim <= 7; ++tim) {
                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ready";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Head Open";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Head Open";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 72 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ribbon Jam";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 68 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Ribbon Empty";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "No Paper";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 66 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Paper Jam";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Paper Empty";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 67 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Cutting";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 75 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Waiting to Press Print Key";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 76 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Waiting to Take Label";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 80 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Printing Batch";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 96 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Pause";
                        this.readBuf = new byte[1024];
                        break;
                    }

                    if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                        printerstatus = "Pause";
                        this.readBuf = new byte[1024];
                        break;
                    }
                }

                return printerstatus;
            } else {
                return "";
            }
        }
    }

    public String status(int delay1, int delay2) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            // int length = false;
            String query = "";
            byte[] message = new byte[]{27, 33, 83};
            this.readBuf = new byte[1024];
            this.strIN = new StringBuilder();
            String printername = "";
            printername = this.printername(delay1);
            int name_length = printername.toString().trim().length();
            if (name_length < 3) {
                return "-1";
            } else {
                this.sendcommand(message);

                try {
                    Thread.sleep((long)delay2);
                } catch (InterruptedException var11) {
                    var11.printStackTrace();
                }

                Thread ReceiveThread = new Thread(new Runnable() {
                    public void run() {
                        boolean mRunning = true;
                        if (mRunning) {
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException var7) {
                                ;
                            }

                            byte[] dest = new byte[64];
                            int timeoutMillis = 100;
                            //int readAmt = true;
                            int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                            if (length > 0) {
                                TSCUSBActivity.this.response = 0;
                                TSCUSBActivity.this.receivelength = length;

                                for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                                    TSCUSBActivity.this.strIN.append((char) TSCUSBActivity.this.readBuf[i]);
                                }
                            } else {
                                TSCUSBActivity.this.response = 1;
                            }
                        }

                    }
                });
                ReceiveThread.start();

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var10) {
                    ;
                }

                if (this.readBuf[0] == 2 && this.readBuf[5] == 3) {
                    for(int tim = 0; tim <= 7; ++tim) {
                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Ready";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Head Open";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 64 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 96 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Head Open";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 72 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Ribbon Jam";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 68 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Ribbon Empty";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "No Paper";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 66 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Paper Jam";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 65 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Paper Empty";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 67 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Cutting";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 75 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Waiting to Press Print Key";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 76 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Waiting to Take Label";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 80 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Printing Batch";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 96 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Pause";
                            this.readBuf = new byte[1024];
                            break;
                        }

                        if (this.readBuf[tim] == 2 && this.readBuf[tim + 1] == 69 && this.readBuf[tim + 2] == 64 && this.readBuf[tim + 3] == 64 && this.readBuf[tim + 4] == 64 && this.readBuf[tim + 5] == 3) {
                            printerstatus = "Pause";
                            this.readBuf = new byte[1024];
                            break;
                        }
                    }

                    return printerstatus;
                } else {
                    return "";
                }
            }
        }
    }

    public String printer_completestatus() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            this.readBuf = new byte[1024];
            this.strIN = new StringBuilder();
            byte[] message = new byte[]{27, 33, 83};
            // int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep(150L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var7) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                            TSCUSBActivity.this.receivelength = length;

                            for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                                TSCUSBActivity.this.strIN.append((char) TSCUSBActivity.this.readBuf[i]);
                            }
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep(500L);
            } catch (InterruptedException var6) {
                ;
            }

            return this.strIN.toString().length() > 1 ? this.strIN.toString() : "";
        }
    }

    public String printer_completestatus(int delaytime, int delaytime2) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            this.readBuf = new byte[1024];
            this.strIN = new StringBuilder();
            byte[] message = new byte[]{27, 33, 83};
            // int length = false;
            String query = "";
            this.sendcommand(message);

            try {
                Thread.sleep((long)delaytime);
            } catch (InterruptedException var9) {
                var9.printStackTrace();
            }

            Thread ReceiveThread = new Thread(new Runnable() {
                public void run() {
                    boolean mRunning = true;
                    if (mRunning) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException var7) {
                            ;
                        }

                        byte[] dest = new byte[64];
                        int timeoutMillis = 100;
                        //int readAmt = true;
                        int length = TSCUSBActivity.mUsbConnection.bulkTransfer(TSCUSBActivity.usbEndpointIn, TSCUSBActivity.this.readBuf, TSCUSBActivity.this.readBuf.length, timeoutMillis);
                        if (length > 0) {
                            TSCUSBActivity.this.response = 0;
                            TSCUSBActivity.this.receivelength = length;

                            for(int i = 0; i < TSCUSBActivity.this.receivelength - 1; ++i) {
                                TSCUSBActivity.this.strIN.append((char) TSCUSBActivity.this.readBuf[i]);
                            }
                        } else {
                            TSCUSBActivity.this.response = 1;
                        }
                    }

                }
            });
            ReceiveThread.start();

            try {
                Thread.sleep((long)delaytime2);
            } catch (InterruptedException var8) {
                ;
            }

            return this.strIN.toString().length() > 1 ? this.strIN.toString() : "";
        }
    }

    public String windowsfont(int x_coordinates, int y_coordinates, int fontsize, String path, String textToPrint) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        Typeface typeface = Typeface.createFromFile(path);
        paint.setTypeface(typeface);
        paint.setTextSize((float)fontsize);
        TextPaint textpaint = new TextPaint(paint);
        StaticLayout staticLayout = new StaticLayout(textToPrint, textpaint, 832, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        int height = staticLayout.getHeight();
        int width = (int)Layout.getDesiredWidth(textToPrint, textpaint);
        if (height > 2378) {
            height = 2378;
        }

        try {
            original_bitmap = Bitmap.createBitmap(width + 8, height, Config.RGB_565);
            Canvas c = new Canvas(original_bitmap);
            c.drawColor(-1);
            c.translate(0.0F, 0.0F);
            staticLayout.draw(c);
        } catch (IllegalArgumentException var32) {
            ;
        } catch (OutOfMemoryError var33) {
            ;
        }

        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
        return "1";
    }

    public String windowsfont(int x_coordinates, int y_coordinates, Bitmap original_bitmap) {
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
        return "1";
    }

    public void sendpicture(int x_coordinates, int y_coordinates, String path) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var25) {
            var25.printStackTrace();
        } catch (SecurityException var26) {
            var26.printStackTrace();
        } catch (IllegalAccessException var27) {
            var27.printStackTrace();
        } catch (NoSuchFieldException var28) {
            var28.printStackTrace();
        }

        original_bitmap = BitmapFactory.decodeFile(path, options);
        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
    }

    public void sendpicture(int x_coordinates, int y_coordinates, Bitmap original_bitmap) {
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var24) {
            var24.printStackTrace();
        } catch (SecurityException var25) {
            var25.printStackTrace();
        } catch (IllegalAccessException var26) {
            var26.printStackTrace();
        } catch (NoSuchFieldException var27) {
            var27.printStackTrace();
        }

        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
    }

    public void sendpicture_halftone(int x_coordinates, int y_coordinates, String path) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var25) {
            var25.printStackTrace();
        } catch (SecurityException var26) {
            var26.printStackTrace();
        } catch (IllegalAccessException var27) {
            var27.printStackTrace();
        } catch (NoSuchFieldException var28) {
            var28.printStackTrace();
        }

        original_bitmap = BitmapFactory.decodeFile(path, options);
        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2halftone(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
    }

    public void sendpicture_CPCL(int x_coordinates, int y_coordinates, String path) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var25) {
            var25.printStackTrace();
        } catch (SecurityException var26) {
            var26.printStackTrace();
        } catch (IllegalAccessException var27) {
            var27.printStackTrace();
        } catch (NoSuchFieldException var28) {
            var28.printStackTrace();
        }

        original_bitmap = BitmapFactory.decodeFile(path, options);
        gray_bitmap = this.bitmap2Gray(original_bitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "EG " + picture_wdith + " " + picture_height + " " + x_axis + " " + y_axis + " ";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = 0;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        String hex_to_string = byteArrayToHex(stream);
        this.sendcommand(command);
        this.sendcommand(hex_to_string.toUpperCase());
        this.sendcommand("\r\n");
    }

    private void sendpicture_resize(int x_coordinates, int y_coordinates, String path, int resize_width, int resize_height) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var28) {
            var28.printStackTrace();
        } catch (SecurityException var29) {
            var29.printStackTrace();
        } catch (IllegalAccessException var30) {
            var30.printStackTrace();
        } catch (NoSuchFieldException var31) {
            var31.printStackTrace();
        }

        original_bitmap = BitmapFactory.decodeFile(path, options);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(original_bitmap, resize_width, resize_height, false);
        gray_bitmap = this.bitmap2Gray(resizedBitmap);
        binary_bitmap = this.gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
    }

    private void sendpicture_resize_halftone(int x_coordinates, int y_coordinates, String path, int resize_width, int resize_height) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap = null;
        Bitmap binary_bitmap = null;
        Options options = new Options();
        options.inPurgeable = true;
        options.inPreferredConfig = Config.ARGB_8888;

        try {
            Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (IllegalArgumentException var28) {
            var28.printStackTrace();
        } catch (SecurityException var29) {
            var29.printStackTrace();
        } catch (IllegalAccessException var30) {
            var30.printStackTrace();
        } catch (NoSuchFieldException var31) {
            var31.printStackTrace();
        }

        original_bitmap = BitmapFactory.decodeFile(path, options);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(original_bitmap, resize_width, resize_height, false);
        gray_bitmap = this.bitmap2Gray(resizedBitmap);
        binary_bitmap = this.gray2halftone(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for(y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for(y = 0; y < Height; ++y) {
            for(int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte)(128 >> x % 8);
                }
            }
        }

        this.sendcommand(command);
        this.sendcommand(stream);
        this.sendcommand("\r\n");
    }

    public Bitmap bitmap2Gray(Bitmap bmSrc) {
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0F);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0.0F, 0.0F, paint);
        return bmpGray;
    }

    public Bitmap lineGrey(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Bitmap linegray = null;
        linegray = image.copy(Config.ARGB_8888, true);

        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                int col = image.getPixel(i, j);
                int alpha = col & -16777216;
                int red = (col & 16711680) >> 16;
                int green = (col & '\uff00') >> 8;
                int blue = col & 255;
                red = (int)(1.1D * (double)red + 30.0D);
                green = (int)(1.1D * (double)green + 30.0D);
                blue = (int)(1.1D * (double)blue + 30.0D);
                if (red >= 255) {
                    red = 255;
                }

                if (green >= 255) {
                    green = 255;
                }

                if (blue >= 255) {
                    blue = 255;
                }

                int newColor = alpha | red << 16 | green << 8 | blue;
                linegray.setPixel(i, j, newColor);
            }
        }

        return linegray;
    }

    public Bitmap gray2Binary(Bitmap graymap) {
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        Bitmap binarymap = null;
        binarymap = graymap.copy(Config.ARGB_8888, true);

        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                int col = binarymap.getPixel(i, j);
                int alpha = col & -16777216;
                int red = (col & 16711680) >> 16;
                int green = (col & '\uff00') >> 8;
                int blue = col & 255;
                int gray = (int)((double)((float)red) * 0.3D + (double)((float)green) * 0.59D + (double)((float)blue) * 0.11D);
                //short gray;
                if (gray <= 127) {
                    gray = 0;
                } else {
                    gray = 255;
                }

                int newColor = alpha | gray << 16 | gray << 8 | gray;
                binarymap.setPixel(i, j, newColor);
            }
        }

        return binarymap;
    }

    public Bitmap gray2halftone(Bitmap graymap) {
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        Bitmap binarymap = null;
        binarymap = graymap.copy(Config.ARGB_8888, true);

        for(int i = 0; i < width - 1; i += 2) {
            for(int j = 0; j < height - 1; j += 2) {
                int col = binarymap.getPixel(i, j);
                int alpha = col & -16777216;
                int red = (col & 16711680) >> 16;
                int green = (col & '\uff00') >> 8;
                int blue = col & 255;
                int gray1 = (int)((double)((float)red) * 0.3D + (double)((float)green) * 0.59D + (double)((float)blue) * 0.11D);
                col = binarymap.getPixel(i + 1, j);
                alpha = col & -16777216;
                red = (col & 16711680) >> 16;
                green = (col & '\uff00') >> 8;
                blue = col & 255;
                int gray2 = (int)((double)((float)red) * 0.3D + (double)((float)green) * 0.59D + (double)((float)blue) * 0.11D);
                col = binarymap.getPixel(i, j + 1);
                alpha = col & -16777216;
                red = (col & 16711680) >> 16;
                green = (col & '\uff00') >> 8;
                blue = col & 255;
                int gray3 = (int)((double)((float)red) * 0.3D + (double)((float)green) * 0.59D + (double)((float)blue) * 0.11D);
                col = binarymap.getPixel(i + 1, j + 1);
                alpha = col & -16777216;
                red = (col & 16711680) >> 16;
                green = (col & '\uff00') >> 8;
                blue = col & 255;
                int gray4 = (int)((double)((float)red) * 0.3D + (double)((float)green) * 0.59D + (double)((float)blue) * 0.11D);
                int helftone = 1020 - (gray1 + gray2 + gray3 + gray4);
                int whiteColor = -1;
                int blackColor = 0;
                if (helftone <= 204) {
                    binarymap.setPixel(i, j, whiteColor);
                    binarymap.setPixel(i + 1, j, whiteColor);
                    binarymap.setPixel(i, j + 1, whiteColor);
                    binarymap.setPixel(i + 1, j + 1, whiteColor);
                } else if (helftone <= 408) {
                    binarymap.setPixel(i, j, whiteColor);
                    binarymap.setPixel(i + 1, j, whiteColor);
                    binarymap.setPixel(i, j + 1, whiteColor);
                    binarymap.setPixel(i + 1, j + 1, blackColor);
                } else if (helftone <= 612) {
                    binarymap.setPixel(i, j, whiteColor);
                    binarymap.setPixel(i + 1, j, blackColor);
                    binarymap.setPixel(i, j + 1, blackColor);
                    binarymap.setPixel(i + 1, j + 1, whiteColor);
                } else if (helftone <= 816) {
                    binarymap.setPixel(i, j, whiteColor);
                    binarymap.setPixel(i + 1, j, blackColor);
                    binarymap.setPixel(i, j + 1, blackColor);
                    binarymap.setPixel(i + 1, j + 1, blackColor);
                } else {
                    binarymap.setPixel(i, j, blackColor);
                    binarymap.setPixel(i + 1, j, blackColor);
                    binarymap.setPixel(i, j + 1, blackColor);
                    binarymap.setPixel(i + 1, j + 1, blackColor);
                }
            }
        }

        return binarymap;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        byte[] var5 = a;
        int var4 = a.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            sb.append(String.format("%02x", b & 255));
        }

        return sb.toString();
    }

    public String WiFi_Default() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            byte[] message = new byte[]{27, 33, 82};
            String default_command = "WLAN DEFAULT\r\n";
            this.sendcommand(default_command);
            this.sendcommand(message);
            return "1";
        }
    }

    public String WiFi_SSID(String SSID) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN SSID \"" + SSID + "\"\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String WiFi_WPA(String WPA) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN WPA \"" + WPA + "\"\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String WiFi_WEP(int number, String WEP) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN WEP " + Integer.toString(number) + "," + "\"" + WEP + "\"\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String WiFi_DHCP() {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN DHCP\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String WiFi_Port(int port) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN PORT " + Integer.toString(port) + "\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String WiFi_StaticIP(String ip, String mask, String gateway) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            String command = "WLAN IP \"" + ip + "\"" + "," + "\"" + mask + "\"" + "," + "\"" + gateway + "\"\r\n";
            this.sendcommand(command);
            return "1";
        }
    }

    public String NFC_Read_data(int delay) {
        if (mUsbConnection == null) {
            return "-1";
        } else {
            this.sendcommand("NFC MODE OFF\r\n");
            this.sendcommand("NFC READ\r\n");

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }

            String nfc_data = this.USBReceive();
            return nfc_data;
        }
    }

    public int NFC_Write_data(String data) {
        this.sendcommand("NFC MODE OFF\r\n");
        this.sendcommand("NFC WRITE \"" + data + "\"" + "\r\n");
        return 1;
    }

    public int NFC_Timeout(int delay) {
        this.sendcommand("NFC TIMEOUT " + delay + "\r\n");
        return 1;
    }
}
