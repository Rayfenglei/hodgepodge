package com.example.ray.myapplication.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String iphone = getResultData();
        Log.i("BroadcastReceiver","CallReceiver: "+iphone);
        String newPhone = "+86"+iphone;
        setResultData(newPhone);


    }
}
