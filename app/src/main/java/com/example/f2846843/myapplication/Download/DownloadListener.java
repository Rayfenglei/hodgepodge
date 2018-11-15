package com.example.f2846843.myapplication.Download;

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onStop();

    void onCanceled();

}
