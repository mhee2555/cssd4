package com.phc.cssd;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tscdll.TSCUSBActivity;

import java.util.HashMap;


public class USBActivity extends Activity {
    private static final String TAG = "UsbHost";

    TextView TscDevice_Text, mDisplayText;
    Button mConnectButton;
    Button mConnectButton2;
    Button mConnectButton3;
    Button mConnectButton4;
    Button mConnectButton5;
    Button mConnectButtonX;
    Button mConnectButtonP;


    UsbManager mUsbManager;
    
    UsbDevice SunmiDevice;
    UsbDevice OrtherDevice;
    UsbDevice TscDevice_1;
    UsbDevice TscDevice_2;
    UsbDevice TscDevice_3;
    UsbDevice TscDevice_4;
    UsbDevice TscDevice_5;

    PendingIntent mPermissionIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        TscDevice_Text = (TextView) findViewById(R.id.text_status);
        mDisplayText = (TextView) findViewById(R.id.text_data);

        mConnectButton = (Button) findViewById(R.id.button_connect);
        mConnectButton2 = (Button) findViewById(R.id.button_connect2);
        mConnectButton3 = (Button) findViewById(R.id.button_connect3);
        mConnectButton4 = (Button) findViewById(R.id.button_connect4);
        mConnectButton5 = (Button) findViewById(R.id.button_connect5);
        mConnectButtonX = (Button) findViewById(R.id.button_connectX);
        mConnectButtonP = (Button) findViewById(R.id.button_connectP);

        mConnectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (TscDevice_1 == null) {
                    return;
                }

                if(mUsbManager.hasPermission(TscDevice_1)){
                    test(TscDevice_1);
                }else {
                    mUsbManager.requestPermission(TscDevice_1, mPermissionIntent);
                }


            }
        });

        mConnectButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (TscDevice_2 == null) {
                    return;
                }

                if(mUsbManager.hasPermission(TscDevice_2)){
                    test(TscDevice_2);
                }else {
                    mUsbManager.requestPermission(TscDevice_2, mPermissionIntent);
                }

            }
        });

        mConnectButton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (TscDevice_3 == null) {
                    return;
                }

                if(mUsbManager.hasPermission(TscDevice_3)){
                    test(TscDevice_3);
                }else {
                    mUsbManager.requestPermission(TscDevice_3, mPermissionIntent);
                }

            }
        });

        mConnectButton4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (TscDevice_4 == null) {
                    return;
                }

                if(mUsbManager.hasPermission(TscDevice_4)){
                    test(TscDevice_4);
                }else {
                    mUsbManager.requestPermission(TscDevice_4, mPermissionIntent);
                }

            }
        });

        mConnectButton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (TscDevice_5 == null) {
                    return;
                }

                if(mUsbManager.hasPermission(TscDevice_5)){
                    test(TscDevice_5);
                }else {
                    mUsbManager.requestPermission(TscDevice_5, mPermissionIntent);
                }

            }
        });

        mConnectButtonX.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (SunmiDevice == null) {
                    return;
                }

                mUsbManager.requestPermission(SunmiDevice, mPermissionIntent);
            }
        });

        mConnectButtonP.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (OrtherDevice == null) {
                    return;
                }

                mUsbManager.requestPermission(OrtherDevice, mPermissionIntent);
            }
        });

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
    }

    private void device(UsbDevice TscDevice_){

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        //Check currently connected devices
        updateDeviceList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mUsbReceiver);
    }

    public void onGotoLoginClick(View v) {
        Intent it = new Intent(USBActivity.this, Login.class);
        startActivity(it);
    }

    /*
     * Receiver to catch user permission responses, which are required in order to actuall
     * interact with a connected device.
     */
    private static final String ACTION_USB_PERMISSION = "com.android.recipes.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false) && device != null) {
                    //Query the device's descriptor
                    getDeviceStatus(device);
                } else {
                    Log.d(TAG, "permission denied for device " + device);
                }
            }
        }
    };

    /*
     * Initiate a control transfer to request the first configuration
     * descriptor of the device.
     */
    //Type: Indicates whether this is a read or write
    // Matches USB_ENDPOINT_DIR_MASK for either IN or OUT
    private static final int REQUEST_TYPE = 0x80;
    //Request: GET_CONFIGURATION_DESCRIPTOR = 0x06
    private static final int REQUEST = 0x06;
    //Value: Descriptor Type (High) and Index (Low)
    // Configuration Descriptor = 0x2
    // Index = 0x0 (First configuration)
    private static final int REQ_VALUE = 0x200;
    private static final int REQ_INDEX = 0x00;
    private static final int LENGTH = 64;

    private void getDeviceStatus(UsbDevice device) {
        UsbDeviceConnection connection = mUsbManager.openDevice(device);
        //Create a sufficiently large buffer for incoming data
        byte[] buffer = new byte[LENGTH];
        connection.controlTransfer(REQUEST_TYPE, REQUEST, REQ_VALUE, REQ_INDEX, buffer, LENGTH, 2000);

        //Parse received data into a description
        String description = parseConfigDescriptor(buffer);

        mDisplayText.setText(description);
        connection.close();
    }

    /*
     * Parse the USB configuration descriptor response per the
     * USB Specification.  Return a printable description of
     * the connected device.
     */
    private static final int DESC_SIZE_CONFIG = 9;
    private String parseConfigDescriptor(byte[] buffer) {
        StringBuilder sb = new StringBuilder();
        //Parse configuration descriptor header
        int totalLength = (buffer[3] &0xFF) << 8;
        totalLength += (buffer[2] & 0xFF);
        //Interface count
        int numInterfaces = (buffer[5] & 0xFF);
        //Configuration attributes
        int attributes = (buffer[7] & 0xFF);
        //Power is given in 2mA increments
        int maxPower = (buffer[8] & 0xFF) * 2;

        sb.append("Configuration Descriptor:\n");
        sb.append("Length: " + totalLength + " bytes\n");
        sb.append(numInterfaces + " Interfaces\n");
        sb.append(String.format("Attributes:%s%s%s\n",
                (attributes & 0x80) == 0x80 ? " BusPowered" : "",
                (attributes & 0x40) == 0x40 ? " SelfPowered" : "",
                (attributes & 0x20) == 0x20 ? " RemoteWakeup" : ""));
        sb.append("Max Power: " + maxPower + "mA\n");

        //The rest of the descriptor is interfaces and endpoints
        int index = DESC_SIZE_CONFIG;
        while (index < totalLength) {
            //Read length and type
            int len = (buffer[index] & 0xFF);
            int type = (buffer[index+1] & 0xFF);
            switch (type) {
                case 0x04: //Interface Descriptor
                    int intfNumber = (buffer[index+2] & 0xFF);
                    int numEndpoints = (buffer[index+4] & 0xFF);
                    int intfClass = (buffer[index+5] & 0xFF);

                    sb.append(String.format("- Interface %d, %s, %d Endpoints\n",
                            intfNumber, nameForClass(intfClass), numEndpoints));
                    break;
                case 0x05: //Endpoint Descriptor
                    int endpointAddr = ((buffer[index+2] & 0xFF));
                    //Number is lower 4 bits
                    int endpointNum = (endpointAddr & 0x0F);
                    //Direction is high bit
                    int direction = (endpointAddr & 0x80);

                    int endpointAttrs = (buffer[index+3] & 0xFF);
                    //Type is the lower two bits
                    int endpointType = (endpointAttrs & 0x3);

                    sb.append(String.format("-- Endpoint %d, %s %s\n",
                            endpointNum,
                            nameForEndpointType(endpointType),
                            nameForDirection(direction) ));
                    break;
            }
            //Advance to next descriptor
            index += len;
        }

        return sb.toString();
    }

    private void test(UsbDevice TscDevice){

        TSCUSBActivity TscUSB = new TSCUSBActivity();

        if(mUsbManager.hasPermission(TscDevice))
        {
            TscUSB.openport(mUsbManager,TscDevice);
            //TscUSB.sendcommand("SIZE 0,0\r\n");
            //TscUSB.sendcommand("GAP 0,0\r\n");
            //TscUSB.sendcommand("CLS\r\n");
            //TscUSB.sendcommand("TEXT 100,100,\"3\",0,1,1,\"123456\"\r\n");

            /*
            System.out.println("printername = " + TscUSB.printername());
            System.out.println("status = " + TscUSB.status());
            System.out.println("getSerialNumber = " + TscDevice_1.getSerialNumber());
            System.out.println("printercodepage = " + TscUSB.printercodepage());
            */

            TscUSB.sendcommand("FORMFEED \r\n");
            //TscUSB.sendcommand("PRINT 1\r\n");
            TscUSB.closeport(3000);
        }

    }

    private void updateDeviceList() {
        HashMap<String, UsbDevice> connectedDevices = mUsbManager.getDeviceList();

        int i = 1;

        if (connectedDevices.isEmpty()) {
            TscDevice_Text.setText("No Devices Currently Connected");

            mConnectButton.setVisibility(TscDevice_1 != null ? View.VISIBLE : View.GONE);
            mConnectButton2.setVisibility(TscDevice_2 != null ? View.VISIBLE : View.GONE);
            mConnectButton3.setVisibility(TscDevice_3 != null ? View.VISIBLE : View.GONE);
            mConnectButton4.setVisibility(TscDevice_4 != null ? View.VISIBLE : View.GONE);
            mConnectButton5.setVisibility(TscDevice_5 != null ? View.VISIBLE : View.GONE);
            mConnectButtonX.setVisibility(SunmiDevice != null ? View.VISIBLE : View.GONE);
            mConnectButtonP.setVisibility(OrtherDevice != null ? View.VISIBLE : View.GONE);

        } else {
            StringBuilder builder = new StringBuilder();

            for (UsbDevice device : connectedDevices.values()) {
                //Use the last device detected (if multiple) to open

                //TscDevice_ = device;

                if(device.getVendorId() == 4611) {

                    System.out.println("START : " + device.getDeviceName());

                    switch(i){
                        case 1 : TscDevice_1 = device; /*mConnectButton.setText(getName(device.getDeviceName()));*/break;
                        case 2 : TscDevice_2 = device; /*mConnectButton2.setText(getName(device.getDeviceName()));*/break;
                        case 3 : TscDevice_3 = device; /*mConnectButton3.setText(getName(device.getDeviceName()));*/break;
                        case 4 : TscDevice_4 = device; /*mConnectButton4.setText(getName(device.getDeviceName()));*/break;
                        case 5 : TscDevice_5 = device; /*mConnectButton5.setText(getName(device.getDeviceName()));*/break;
                    }
                    i++;
                }else if(device.getVendorId() == 2965){
                    SunmiDevice = device;
                }else {


                    UsbInterface intf = device.getInterface(0);

                    System.out.println(device.getDeviceClass() + " == " + UsbConstants.USB_CLASS_PRINTER);

                    if( intf.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER ){
                        OrtherDevice = device;
                    }
                }

                builder.append(readDevice(device));
                builder.append("\n\n");

            }

            mConnectButton.setVisibility(TscDevice_1 != null ? View.VISIBLE : View.GONE);
            mConnectButton2.setVisibility(TscDevice_2 != null ? View.VISIBLE : View.GONE);
            mConnectButton3.setVisibility(TscDevice_3 != null ? View.VISIBLE : View.GONE);
            mConnectButton4.setVisibility(TscDevice_4 != null ? View.VISIBLE : View.GONE);
            mConnectButton5.setVisibility(TscDevice_5 != null ? View.VISIBLE : View.GONE);
            mConnectButtonX.setVisibility(SunmiDevice != null ? View.VISIBLE : View.GONE);
            mConnectButtonP.setVisibility(OrtherDevice != null ? View.VISIBLE : View.GONE);

            TscDevice_Text.setText(builder.toString());
            //mConnectButton.setEnabled(true);
        }
    }

    private String getName(String DeviceName){
        //System.out.println(DeviceName);
        return DeviceName.toLowerCase().replaceAll("/dev/bus/usb/001/","");
    }

    /*
     * Enumerate the endpoints and interfaces on the connected device.
     * We do not need permission to do anything here, it is all "publicly available"
     * until we try to connect to an actual device.
     */
    private String readDevice(UsbDevice device) {
        StringBuilder sb = new StringBuilder();
        /*
        sb.append(" Device Name : " + device.getDeviceName() + "\n");
        sb.append(" Id      : " + device.getDeviceId() + "\n");
        sb.append(" Class   : " + device.getDeviceClass() + "\n");
        sb.append(" Prod.Id : " + device.getProductId() + "\n");
        sb.append(" Vendor.Id : " + device.getVendorId() + "\n");
        sb.append(" getSerialNumber : " + device.getSerialNumber() + "\n");
        sb.append(" getManufacturerName() : " + device.getManufacturerName() + "\n");
        */

        sb.append(device.toString() + "\n");





        sb.append(String.format(
                "Device Class: %s -> Subclass: 0x%02x -> Protocol: 0x%02x\n",
                nameForClass(device.getDeviceClass()),
                device.getDeviceSubclass(), device.getDeviceProtocol()));

        for (int i = 0; i < device.getInterfaceCount(); i++) {
            UsbInterface intf = device.getInterface(i);
            sb.append(String
                    .format("+--Interface %d Class: %s -> Subclass: 0x%02x -> Protocol: 0x%02x\n",
                            intf.getId(),
                            nameForClass(intf.getInterfaceClass()),
                            intf.getInterfaceSubclass(),
                            intf.getInterfaceProtocol()));

            for (int j = 0; j < intf.getEndpointCount(); j++) {
                UsbEndpoint endpoint = intf.getEndpoint(j);
                sb.append(String.format("  +---Endpoint %d: %s %s\n",
                        endpoint.getEndpointNumber(),
                        nameForEndpointType(endpoint.getType()),
                        nameForDirection(endpoint.getDirection())));
            }
        }

        return sb.toString();
    }

    /* Helper Methods to Provide Readable Names for USB Constants */

    private String nameForClass(int classType) {
        switch (classType) {
            case UsbConstants.USB_CLASS_APP_SPEC:
                return String.format("Application Specific 0x%02x", classType);
            case UsbConstants.USB_CLASS_AUDIO:
                return "Audio";
            case UsbConstants.USB_CLASS_CDC_DATA:
                return "CDC Control";
            case UsbConstants.USB_CLASS_COMM:
                return "Communications";
            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "Content Security";
            case UsbConstants.USB_CLASS_CSCID:
                return "Content Smart Card";
            case UsbConstants.USB_CLASS_HID:
                return "Human Interface Device";
            case UsbConstants.USB_CLASS_HUB:
                return "Hub";
            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "Mass Storage";
            case UsbConstants.USB_CLASS_MISC:
                return "Wireless Miscellaneous";
            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "(Defined Per Interface)";
            case UsbConstants.USB_CLASS_PHYSICA:
                return "Physical";
            case UsbConstants.USB_CLASS_PRINTER:
                return "Printer";
            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "Still Image";
            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return String.format("Vendor Specific 0x%02x", classType);
            case UsbConstants.USB_CLASS_VIDEO:
                return "Video";
            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "Wireless Controller";
            default:
                return String.format("0x%02x", classType);
        }
    }

    private String nameForEndpointType(int type) {
        switch (type) {
            case UsbConstants.USB_ENDPOINT_XFER_BULK:
                return "Bulk";
            case UsbConstants.USB_ENDPOINT_XFER_CONTROL:
                return "Control";
            case UsbConstants.USB_ENDPOINT_XFER_INT:
                return "Interrupt";
            case UsbConstants.USB_ENDPOINT_XFER_ISOC:
                return "Isochronous";
            default:
                return "Unknown Type";
        }
    }

    private String nameForDirection(int direction) {
        switch (direction) {
            case UsbConstants.USB_DIR_IN:
                return "IN";
            case UsbConstants.USB_DIR_OUT:
                return "OUT";
            default:
                return "Unknown Direction";
        }
    }
}