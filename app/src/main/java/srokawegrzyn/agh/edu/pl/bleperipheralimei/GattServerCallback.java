//package srokawegrzyn.agh.edu.pl.bleperipheralimei;
//
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattServer;
//import android.bluetooth.BluetoothGattServerCallback;
//import android.bluetooth.BluetoothGattService;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.Arrays;
//
///**
// * Created by maciek on 03.06.16.
// */
//public class GattServerCallback extends BluetoothGattServerCallback{
//    public static String TAG = "X";
//
//    @Override
//        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
//            int x = 5;
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                /*if (newState == BluetoothGatt.STATE_CONNECTED) {
//                    mBluetoothDevices.add(device);
//                    updateConnectedDevicesStatus();
//                    Log.v(TAG, "Connected to device: " + device.getAddress());
//                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
//                    mBluetoothDevices.remove(device);
//                    updateConnectedDevicesStatus();
//                    Log.v(TAG, "Disconnected from device");
//                }*/
//            } else {
//                /*mBluetoothDevices.remove(device);
//                updateConnectedDevicesStatus();
//                // There are too many gatt errors (some of them not even in the documentation) so we just
//                // show the error to the user.
//                final String errorMessage = getString(R.string.status_errorWhenConnecting) + ": " + status;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(Peripheral.this, errorMessage, Toast.LENGTH_LONG).show();
//                    }
//                });
//                Log.e(TAG, "Error when connecting: " + status);*/
//            }
//        }
//
//        @Override
//        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
//                                                BluetoothGattCharacteristic characteristic) {
//            Log.d(TAG, "Device tried to read characteristic: " + characteristic.getUuid());
//            Log.d(TAG, "Value: " + Arrays.toString(characteristic.getValue()));
//
//            if (offset != 0) {
//                gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset,
//            /* value (optional) */ null);
//                return;
//            }
//            gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
//                    offset, characteristic.getValue());
//
//            /*if (offset != 0) {
//                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset,
//            *//* value (optional) *//* null);
//                return;
//            }
//            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
//                    offset, characteristic.getValue());*/
//        }
//
//        @Override
//        public void onNotificationSent(BluetoothDevice device, int status) {
//            int x = 5;
//            Log.v(TAG, "Notification sent. Status: " + status);
//        }
//
//        @Override
//        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId,
//                                                 BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
//                                                 int offset, byte[] value) {
//            int x = 5;
//            Log.v(TAG, "Characteristic Write request: " + Arrays.toString(value));
//            /*int status = mCurrentServiceFragment.writeCharacteristic(characteristic, offset, value);
//            if (responseNeeded) {
//                mGattServer.sendResponse(device, requestId, status,*/
////            /* No need to respond with an offset */ 0,
////            /* No need to respond with a value */ null);
////            }
//        }
//
//        @Override
//        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId,
//                                             BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded,
//                                             int offset,
//                                             byte[] value) {
//            Log.v(TAG, "Descriptor Write Request " + descriptor.getUuid() + " " + Arrays.toString(value));
//            int x = 5;
//            if(responseNeeded) {
//                /*mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,*/
////            /* No need to respond with offset */ 0,
////            /* No need to respond with a value */ null);
//            }
//        }
//
//    @Override
//    public void onServiceAdded(int status, BluetoothGattService service) {
//        int x = 5;
//    }
//
//    @Override
//    public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
//        int x = 5;
//    }
//
//    @Override
//    public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
//        int x = 5;
//    }
//
//    @Override
//    public void onMtuChanged(BluetoothDevice device, int mtu) {
//        int x = 5;
//    }
//}
