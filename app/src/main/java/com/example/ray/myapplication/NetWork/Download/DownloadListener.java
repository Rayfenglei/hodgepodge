package com.example.ray.myapplication.NetWork.Download;

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onStop();

    void onCanceled();

}
