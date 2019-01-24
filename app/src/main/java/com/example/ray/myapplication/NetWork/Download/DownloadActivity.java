package com.example.ray.myapplication.NetWork.Download;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ray.myapplication.R;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartBtn,mStopBtn,mCancleBtn;
    private DownloadService.DownloadBinder downloadBinder;
    /* ServiceConnection代表与服务的连接，它只有两个方法：onServiceConnected和onServiceDisconnected，
    前者是在操作者在连接一个服务成功时被调用，而后者是在服务崩溃或被杀死导致的连接中断时被调用 */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        /* onServiceConnected 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
        通过这个IBinder对象，实现宿主和Service的交互。 */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 获取Binder
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        init();

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent); // 启动服务
        bindService(intent, serviceConnection, BIND_AUTO_CREATE); // 绑定服务
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    private void init(){
        mStartBtn = findViewById(R.id.start_btn);
        mStopBtn = findViewById(R.id.stop_btn);
        mCancleBtn = findViewById(R.id.cancle_btn);
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (downloadBinder == null) {
            return;
        }
        switch (view.getId()){
            case R.id.start_btn:
                String url = "https://dldir1.qq.com/weixin/android/weixin672android1340.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.stop_btn:
                downloadBinder.stopDownload();
                break;
            case R.id.cancle_btn:
                downloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
