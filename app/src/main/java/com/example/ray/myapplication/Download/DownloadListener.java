package com.example.ray.myapplication.Download;

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onStop();

    void onCanceled();

}
