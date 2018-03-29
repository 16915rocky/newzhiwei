package com.chinamobile.newzhiwei.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ai.appframe2.complex.util.e.K;
import com.zyw.horrarndoo.sdk.utils.AppUtils;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2018/3/25.
 */

public class PhoneConfigMessageUtils {
    //获取手机串码
    public static String getDeviceId(){
        TelephonyManager TelephonyMgr = (TelephonyManager)AppUtils.getContext().getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(AppUtils.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        String imei = TelephonyMgr.getDeviceId();
        try {
            imei = K.j(imei==null?"":imei);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }
}
