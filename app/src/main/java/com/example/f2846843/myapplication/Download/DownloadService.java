package com.example.f2846843.myapplication.Download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadService extends Service {
    private DownloadTask downloadTask;

    private String downloadUrl;

    //创建线程池 大小为3
    private  static ExecutorService Tasks = Executors.newFixedThreadPool(3);

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            //发送广播 发送下载进度
            Intent intent = new Intent();
            intent.putExtra("count", progress);
            intent.setAction("setProgressBar");
            sendBroadcast(intent);
        }


        @Override
        public void onSuccess() {
            downloadTask = null;
            // 下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            // 下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStop() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }


    };

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class DownloadBinder extends Binder {

        public void startDownload(String url) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                //downloadTask.execute(downloadUrl);
                downloadTask.executeOnExecutor(Tasks,downloadUrl);
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();

        }

        public void stopDownload() {
            if (downloadTask != null) {
                downloadTask.stopDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    // 取消下载时需将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
