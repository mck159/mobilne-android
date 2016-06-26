package srokawegrzyn.agh.edu.pl.bleperipheralimei;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID
            .fromString("00002902-0000-1000-8000-00805f9b34f1");
    private static final UUID BATTERY_SERVICE_UUID = UUID
            .fromString("0000180F-0000-1000-8000-00805f9b34f2");
    private static final UUID BATTERY_LEVEL_UUID = UUID
            .fromString("00002A19-0000-1000-8000-00805f9b34f3");
    private BluetoothLeAdvertiser bleAdvertiser;
    private AdvertiseSettings mAdvSettings;
    private AdvertiseData mAdvData;
    private AdvertiseCallback bleAdveriseCallback = new MyAdvertiseCallback();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private BluetoothGattService mBatteryService;
    private BluetoothGattServer gattServer;
    private PhoneData phoneData;
    private boolean isBleAdvertisting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonState((Button) findViewById(R.id.button));

        phoneData = new PhoneData((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        BluetoothGattCharacteristic mBatteryLevelCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ);

        mBatteryLevelCharacteristic.addDescriptor(
                new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_UUID,
                        (BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE))

        );
        mBatteryLevelCharacteristic.setValue("CHR1");

        mBatteryService = new BluetoothGattService(BATTERY_SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mBatteryService.addCharacteristic(mBatteryLevelCharacteristic);




        bluetoothAdapter = bluetoothManager.getAdapter();
        bleAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

        mAdvSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .setConnectable(true)
                .build();
        mAdvData = new AdvertiseData.Builder()
                .setIncludeDeviceName(false)
                .setIncludeTxPowerLevel(true)
                .addServiceUuid(new ParcelUuid(BATTERY_SERVICE_UUID))
                .build();


        bleAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
    }

    public void startBLE() {
        bleAdvertiser.startAdvertising(mAdvSettings, mAdvData, bleAdveriseCallback);
        gattServer = bluetoothManager.openGattServer(this, new BluetoothGattServerCallback() {
            public String TAG = "X";

            @Override
            public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            }

            @Override
            public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                                                    BluetoothGattCharacteristic characteristic) {
                Log.d(TAG, "Device tried to read characteristic: " + characteristic.getUuid());
                Log.d(TAG, "Value: " + Arrays.toString(characteristic.getValue()));

                if (offset != 0) {
                    gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset,
            /* value (optional) */ null);
                    return;
                }

                gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                        offset, String.format("%s:%s:%s", phoneData
                                .getManufacturer(), phoneData.getModel(), phoneData.getIMEI()).getBytes(StandardCharsets.UTF_8));

            }

            @Override
            public void onNotificationSent(BluetoothDevice device, int status) {
                int x = 5;
                Log.v(TAG, "Notification sent. Status: " + status);
            }

            @Override
            public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId,
                                                     BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
                                                     int offset, byte[] value) {
                Log.v(TAG, "Characteristic Write request: " + Arrays.toString(value));
            }

            @Override
            public void onDescriptorWriteRequest(BluetoothDevice device, int requestId,
                                                 BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded,
                                                 int offset,
                                                 byte[] value) {
                Log.v(TAG, "Descriptor Write Request " + descriptor.getUuid() + " " + Arrays.toString(value));
            }

            @Override
            public void onServiceAdded(int status, BluetoothGattService service) {
                int x = 5;
            }

            @Override
            public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            }

            @Override
            public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            }

            @Override
            public void onMtuChanged(BluetoothDevice device, int mtu) {
            }
        });
        gattServer.addService(mBatteryService);

    }

    public void stopBLE() {
        bleAdvertiser.stopAdvertising(bleAdveriseCallback);
        gattServer.close();
    }

    public void buttonMainClick(View v) {
        if(isBleAdvertisting) {
            this.stopBLE();

        } else {
            this.startBLE();
        }
        isBleAdvertisting = !isBleAdvertisting;
        setButtonState((Button) v);
    }

    public void setButtonState(Button v) {
        v.setText(!isBleAdvertisting ? "Start BLE" : "Stop BLE");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isBleAdvertisting", isBleAdvertisting);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isBleAdvertisting = savedInstanceState.getBoolean("isBleAdvertisting");

        super.onRestoreInstanceState(savedInstanceState);
    }
}
