package com.example.ray.myapplication.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SDReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED)){
            Log.i("BroadcastReceiver","SDReceiver SD卡就绪");
        }else if (action.equals(Intent.ACTION_MEDIA_REMOVED)){
            Log.i("BroadcastReceiver","SDReceiver SD卡拔出");
        }else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTABLE)){
            Log.i("BroadcastReceiver","SDReceiver SD卡卸载");
        }

    }
}
