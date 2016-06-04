package srokawegrzyn.agh.edu.pl.bleperipheralimei;

import android.bluetooth.le.AdvertiseCallback;
import android.util.Log;

/**
 * Created by maciek on 03.06.16.
 */
public class MyAdvertiseCallback extends AdvertiseCallback {
    public static String TAG = "D";
        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.e(TAG, "Not broadcasting: " + errorCode);
            String statusText;
            switch (errorCode) {
                case ADVERTISE_FAILED_ALREADY_STARTED:
                    statusText = "status_advertising";
                    Log.w(TAG, "App was already advertising");
                    break;
                case ADVERTISE_FAILED_DATA_TOO_LARGE:
                    statusText = "status_advDataTooLarge";
                    break;
                case ADVERTISE_FAILED_FEATURE_UNSUPPORTED:
                    statusText = "status_advFeatureUnsupported";
                    break;
                case ADVERTISE_FAILED_INTERNAL_ERROR:
                    statusText = "status_advInternalError";
                    break;
                case ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                    statusText = "status_advTooManyAdvertisers";
                    break;
                default:
                    statusText = "status_notAdvertising";
                    Log.wtf(TAG, "Unhandled error: " + errorCode);
            }
        }
}
