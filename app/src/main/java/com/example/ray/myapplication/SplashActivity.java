package com.example.ray.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.example.ray.myapplication.UIView.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {
    //权限
    public  String[] COMMON_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
    }
    //检查权限
    private void checkPermission() {
        if (isPermissionGranted(this, COMMON_PERMISSION)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            requestPermissions(this, COMMON_PERMISSION,1);
        }
    }
    //申请权限
    public void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        List<String> ps = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            //判断已经通过的权限
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ps.add(permissions[i]);
            }
        }
        //把未通过的权限添加到申请列表
        if (!ps.isEmpty()) {
            String[] p = new String[ps.size()];
            for (int i = 0; i < ps.size(); i++) {
                p[i] = ps.get(i);
            }
            //申请权限
            ActivityCompat.requestPermissions(activity, p, requestCode);
        }
    }
    //判断权限是否全部通过
    public boolean isPermissionGranted(Activity activity, String[] permissions) {
        boolean granted = true;
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        return granted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (isPermissionGranted(this, COMMON_PERMISSION)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                finish();
            }
        }
    }
}
