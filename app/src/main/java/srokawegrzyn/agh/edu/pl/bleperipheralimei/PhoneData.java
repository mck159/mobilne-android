package srokawegrzyn.agh.edu.pl.bleperipheralimei;

import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by maciek on 04.06.16.
 */
public class PhoneData {
    TelephonyManager telephonyManager;

    public PhoneData(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    public String getManufacturer() {
        return Build.MANUFACTURER;
    }
    public String getModel() {
        return Build.MODEL;
    }
    public String getIMEI() {
        return telephonyManager.getDeviceId();
    }
}
