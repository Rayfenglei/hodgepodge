package com.example.ray.myapplication.News;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.UUID;

import static android.content.Context.TELEPHONY_SERVICE;

public class ApiUtils {
    private static final String TAG = "ApiUtils";

    public static final String DEFAULT_ANDROID_ID= "";
    private static final String APP_SECRET = "";
    private static final String APP_KEY = "";
    /*private static final String APP_SECRET = "28854258e957aefe7d78d447322672b566aeff56";
    private static final String APP_KEY = "cc02edf5830592a8e747febb97995f37";*/

    public static String getAndroidId(Context context) {
        String androidId = null;
        if (context != null) {
            try {
                androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Throwable e) {
            }
        }

        return TextUtils.isEmpty(androidId) ? DEFAULT_ANDROID_ID : androidId;
    }

    public static String getImei(Context context) {
        String imei = null;
        try {
            if (context != null && context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    imei = telephonyManager.getDeviceId();
                }
            }
        } catch (Throwable e) {
            Log.e("AndroidUtils", "getImei " + e.getMessage());
        }
        return imei == null ? "" : imei;
    }


    public static String getDeviceUUID(Context context) {
        if (context == null) {
            return UUID.randomUUID().toString();
        }

        UUID uuid;
        String androidId = getAndroidId(context);
        if (androidId.equals(DEFAULT_ANDROID_ID)) {
            String deviceId = getImei(context);
            if (TextUtils.isEmpty(deviceId)) {
                uuid = UUID.randomUUID();
            } else {
                uuid = UUID.nameUUIDFromBytes(deviceId.getBytes());
            }
        } else {
            uuid = UUID.nameUUIDFromBytes(androidId.getBytes());
        }

        return uuid.toString();
    }

    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static String restoreFieldString(Context context, String key, String defaultValue) {
        if (context == null) {
            return defaultValue;
        }

        SharedPreferences spf = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = spf.getString(key, defaultValue);
        return value;
    }

    public static void saveField(Context context, String key, String value) {
        if (context == null) {
            return;
        }

        SharedPreferences spf = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getCommonTK(String data, String time){
        String beforeTK;
        if(!TextUtils.isEmpty(data)){
            beforeTK = APP_SECRET + ":" + data + ":" + time;
        }else {
            beforeTK = APP_SECRET + "::" + time;
        }
        return MD5(beforeTK);
    }


    /** 加密
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String MD5(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @return
     */
    public static String str2HexStr() {
        String str = APP_KEY;
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }


    public static void saveObject(Context context, Object obj, String name) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(name, 0))) {
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getSaveObject(Context context, String name) {
        T obj = null;
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput(name))) {
            obj = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    return networkInfo.getType();
                }
            }
        }
        return -1;
    }

    public static boolean isWifiConnected(Context context) {
        int type = getConnectedType(context);
        return type == ConnectivityManager.TYPE_WIFI;
    }


}
