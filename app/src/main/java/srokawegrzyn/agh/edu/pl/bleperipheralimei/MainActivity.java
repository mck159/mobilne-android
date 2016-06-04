package srokawegrzyn.agh.edu.pl.bleperipheralimei;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID
            .fromString("00002902-0000-1000-8000-00805f9b34f1");
    private static final UUID BATTERY_SERVICE_UUID = UUID
            .fromString("0000180F-0000-1000-8000-00805f9b34f2");
    private static final UUID BATTERY_LEVEL_UUID = UUID
            .fromString("00002A19-0000-1000-8000-00805f9b34f3");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        BluetoothGattCharacteristic mBatteryLevelCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ);

        mBatteryLevelCharacteristic.addDescriptor(
                getClientCharacteristicConfigurationDescriptor());

        BluetoothGattService mBatteryService = new BluetoothGattService(BATTERY_SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mBatteryService.addCharacteristic(mBatteryLevelCharacteristic);


        BluetoothGattServer gattServer = bluetoothManager.openGattServer(this, new GattServerCallback());
        gattServer.addService(mBatteryService);

        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothLeAdvertiser bleAdvert iser = bluetoothAdapter.getBluetoothLeAdvertiser();

        AdvertiseSettings mAdvSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .setConnectable(true)
                .build();
        AdvertiseData mAdvData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                .addServiceUuid(getServiceUUID())
                .build();


        bleAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        bleAdvertiser.startAdvertising(mAdvSettings, mAdvData, new MyAdvertiseCallback());
    }

    public static BluetoothGattDescriptor getClientCharacteristicConfigurationDescriptor() {
        return new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_UUID,
                (BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
    }
    public ParcelUuid getServiceUUID() {
        return new ParcelUuid(BATTERY_SERVICE_UUID);
    }

    void writeToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
