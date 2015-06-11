package com.example.rvakash.swipetrail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class Android extends Fragment {


    // UUIDs for UAT service and associated characteristics.
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    // UUID for the BTLE client characteristic which is necessary for notifications.
    public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    // UI elements
    private TextView messages;
    private EditText input;
    Boolean gattsuccess = false;
    // BTLE state
    private BluetoothAdapter adapter;
    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic tx;
    private BluetoothGattCharacteristic rx;

    String founddevice, storeddevice;
    Button sendbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = BluetoothAdapter.getDefaultAdapter();

    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View android = inflater.inflate(R.layout.android_frag, container, false);

            // Grab references to UI elements.
            messages = (TextView) android.findViewById(R.id.messages);
            input = (EditText) android.findViewById(R.id.input);
            sendbutton = (Button) android.findViewById(R.id.send);
            sendbutton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message= input.getText().toString();
                    //sentfromtemp();
//                    Bundle args = fragment.getArguments();

//                    if (args  != null && args.containsKey("sent")) {
//                         message = args.getString("sent");
//                        Toast toast = new Toast(getActivity().getApplicationContext());
//                        Toast.makeText(getActivity().getApplicationContext(), "inandroid: " + message, toast.LENGTH_LONG).show();

                    //}
                    if (tx == null || message == null || message.isEmpty()) {
                        // Do nothing if there is no device or message to send.
                        return;
                    }
                    // Update TX characteristic value.  Note the setValue overload that takes a byte array must be used.
                    tx.setValue(message.getBytes(Charset.forName("UTF-8")));
                    if (gatt.writeCharacteristic(tx)) {
                        writeLine("Sent: " + message);
                    } else {
                        writeLine("Couldn't write TX characteristic!");
                    }

                }
            });

            //sentfromtemp();
//            Bundle args = this.getArguments();

//            if (args  != null && args.containsKey("sent")) {
//               String message = args.getString("sent", "not sent");
//                Toast toast = new Toast(getActivity().getApplicationContext());
//                Toast.makeText(getActivity().getApplicationContext(), "inandroid: " + message, toast.LENGTH_LONG).show();

//            }
            ((TextView) android.findViewById(R.id.textView)).setText("In message");

    return android;
    }





    // Main BTLE device callback where much of the logic occurs.
    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        // Called whenever the device connection state changes, i.e. from disconnected to connected.
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                writeLine("Connected!");
                // Discover services.
                if (!gatt.discoverServices()) {
                    writeLine("Failed to start discovering services!");
                }
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                writeLine("Disconnected!");
            } else {
                writeLine("Connection state changed.  New state: " + newState);
            }
        }





        // Called when services have been discovered on the remote device.
        // It seems to be necessary to wait for this discovery to occur before
        // manipulating any services or characteristics.
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                writeLine("Service discovery completed!");
                gattsuccess = true;

            } else {
                writeLine("Service discovery failed with status: " + status);
            }
            // Save reference to each characteristic.
            tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
            rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);
            // Setup notifications on RX characteristic changes (i.e. data received).
            // First call setCharacteristicNotification to enable notification.
            if (!gatt.setCharacteristicNotification(rx, true)) {
                writeLine("Couldn't set notifications for RX characteristic!");
            }
            // Next update the RX characteristic's client descriptor to enable notifications.
            if (rx.getDescriptor(CLIENT_UUID) != null) {
                BluetoothGattDescriptor desc = rx.getDescriptor(CLIENT_UUID);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                if (!gatt.writeDescriptor(desc)) {
                    writeLine("Couldn't write RX client descriptor value!");
                }
            } else {
                writeLine("Couldn't get RX client descriptor!");
            }
        }

        // Called when a remote characteristic changes (like the RX characteristic).
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            writeLine("Received: " + characteristic.getStringValue(0));
        }
    };

    // BTLE device scanning callback.
    LeScanCallback scanCallback = new LeScanCallback() {
        // Called when a device is found.
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            writeLine("Found device: " + bluetoothDevice.getAddress());
            //Store new ble device id in founddevice
            founddevice = bluetoothDevice.getAddress();
            //Check if (1st time user or not) the mobile already is registered with a ble device id
            //      else create a new file and store the founddevice id.(for a 1st time user)
            String Settingg = "Settin.txt";
            //checking if Settings.txt file is present in app internal storage
            //else createfile, pass founddevice id as argument
            if (fileExistance(Settingg)) {
                writeLine("File exists calling readfile");
            } else {
                //1st time user. create file and store new ble device id
                writeLine("file does not exist calling createfile");
                try {
                    createfile(founddevice);
                } catch (IOException e) {
                    System.err.println("Caught IOException in createfile: " + e.getMessage());
//                    e.printStackTrace();
                }
            }
            //Readfile from the app internal storage
            try {
                readfile();
            } catch (IOException e) {
                System.err.println("Caught IOException in readfile: " + e.getMessage());
                //e.printStackTrace();
            }
            //Check if new found ble device id is equal to stored ble device id
            if (founddevice.equals(storeddevice)) {
                writeLine("found device = stored device");
                // Check if the device has the UART service.
                if (parseUUIDs(bytes).contains(UART_UUID)) {
                    // Found a device, stop the scan.
                    adapter.stopLeScan(scanCallback);
                    writeLine("Found UART service!");
                    // Connect to the device.
                    // Control flow will now go to the callback functions when BTLE events occur.
                    gatt = bluetoothDevice.connectGatt(getActivity().getApplicationContext(), false, callback);
                }
            } else {
                writeLine("this is not your device");
            }
        }
    };
    //Check if Settings.txt file exists. Return true or false

    public boolean fileExistance(String fname) {
        File file = getActivity().getBaseContext().getFileStreamPath(fname);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    //Create file Settings.txt in apps internal storage and write the new found (ble) device id
    public void createfile(String device) throws IOException {
        founddevice = device;
       // FileOutputStream fos = openFileOutput("Settin.txt", Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(getActivity().openFileOutput("Settin.txt", Context.MODE_PRIVATE));
        osw.write(founddevice);
        osw.close();
        writeLine("file written");

    }



    //Read file Settings.txt from apps internal storage and load the stored (ble) device id to storeddevice
    public void readfile()  throws IOException {
        //FileInputStream isr = openFileInput("Settin.txt");
        InputStreamReader isr = new InputStreamReader(getActivity().openFileInput("Settin.txt"));
        BufferedReader bis = new BufferedReader(isr);
        StringBuffer b = new StringBuffer();
        while (bis.ready()) {
            char c = (char) bis.read();
            b.append(c);
           // writeLine("in while loop");
        }
        writeLine("stored device=" + b.toString());
        storeddevice = b.toString();
    }

    // OnResume, called right before UI is displayed.  Start the BTLE connection.
    @Override
    public void onResume() {
        super.onResume();
        if (!gattsuccess) {
            // Scan for all BTLE devices.
            // The first one with the UART service will be chosen--see the code in the scanCallback.
            writeLine("Scanning for devices...");
            adapter.startLeScan(scanCallback);

        }
    }
    @Override
    public void onPause (){
        super.onPause();
        //sentfromtemp();
    }
    //@Override
    public void sentfromtemp() {
        super.getEnterTransition();
//            Bundle bundle = this.getArguments();
//            String message = bundle.getString("sent", "not sent");
        String message = "not sent";
        Bundle args = this.getArguments();

        if (args  != null && args.containsKey("sent")) {
            message = args.getString("sent", "not sent");
        }
        else{
            writeLine(message);
        }
        if (message.equals("not sent")){
            writeLine("not sent");
        }
        else {
            if (tx == null || message == null || message.isEmpty()) {
                // Do nothing if there is no device or message to send.
                return;
            }
            // Update TX characteristic value.  Note the setValue overload that takes a byte array must be used.
            tx.setValue(message.getBytes(Charset.forName("UTF-8")));
            if (gatt.writeCharacteristic(tx)) {
                writeLine("Sent: " + message);
            } else {
                writeLine("Couldn't write TX characteristic!");
            }
        }
    }


    // OnStop, called right before the activity loses foreground focus.  Close the BTLE connection.
   /* @Override
    protected void onStop() {
        super.onStop();
        if (gatt != null) {
            // For better reliability be careful to disconnect and close the connection.
            gatt.disconnect();
            gatt.close();
            gatt = null;
            tx = null;
            rx = null;
        }
    }

    // Handler for mouse click on the send button.

    public void sendClick(View view) {
        String message = input.getText().toString();
        if (tx == null || message == null || message.isEmpty()) {
            // Do nothing if there is no device or message to send.
            return;
        }
        // Update TX characteristic value.  Note the setValue overload that takes a byte array must be used.
        tx.setValue(message.getBytes(Charset.forName("UTF-8")));
        if (gatt.writeCharacteristic(tx)) {
            writeLine("Sent: " + message);
        } else {
            writeLine("Couldn't write TX characteristic!");
        }

    }
*/

    // Write some text to the messages text view.
    // Care is taken to do this on the main UI thread so writeLine can be called
    // from any thread (like the BTLE callback).
    private void writeLine(final CharSequence text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                System.out.println("in writeline before messages");
                messages.append(text);
                messages.append("\n");
                System.out.println("in writeline after messages");
            }
        });
    }

    // Filtering by custom UUID is broken in Android 4.3 and 4.4, see:
    //   http://stackoverflow.com/questions/18019161/startlescan-with-128-bit-uuids-doesnt-work-on-native-android-ble-implementation?noredirect=1#comment27879874_18019161
    // This is a workaround function from the SO thread to manually parse advertisement data.
    private List<UUID> parseUUIDs(final byte[] advertisedData) {
        List<UUID> uuids = new ArrayList<UUID>();

        int offset = 0;
        while (offset < (advertisedData.length - 2)) {
            int len = advertisedData[offset++];
            if (len == 0)
                break;

            int type = advertisedData[offset++];
            switch (type) {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (len > 1) {
                        int uuid16 = advertisedData[offset++];
                        uuid16 += (advertisedData[offset++] << 8);
                        len -= 2;
                        uuids.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", uuid16)));
                    }
                    break;
                case 0x06:// Partial list of 128-bit UUIDs
                case 0x07:// Complete list of 128-bit UUIDs
                    // Loop through the advertised 128-bit UUID's.
                    while (len >= 16) {
                        try {
                            // Wrap the advertised bits and order them.
                            ByteBuffer buffer = ByteBuffer.wrap(advertisedData, offset++, 16).order(ByteOrder.LITTLE_ENDIAN);
                            long mostSignificantBit = buffer.getLong();
                            long leastSignificantBit = buffer.getLong();
                            uuids.add(new UUID(leastSignificantBit,
                                    mostSignificantBit));
                        } catch (IndexOutOfBoundsException e) {
                            // Defensive programming.
                            //Log.e(LOG_TAG, e.toString());
                            continue;
                        } finally {
                            // Move the offset to read the next uuid.
                            offset += 15;
                            len -= 16;
                        }
                    }
                    break;
                default:
                    offset += (len - 1);
                    break;
            }
        }
        return uuids;
    }
}
