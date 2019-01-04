package com.example.ray.myapplication.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //获取短信内容
        Bundle bundle = intent.getExtras();
        //返回Object[]类型
        Object[] objects = (Object[]) bundle.get("pdus");
        //遍历短信内容
        for (Object object : objects){
            //把数组转化为短信
            SmsMessage sms  = SmsMessage.createFromPdu((byte[])object, Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            //获取号码 和 内容
            String toPhone = sms.getOriginatingAddress();
            String smsContent = sms.getMessageBody();
            Log.i("BroadcastReceiver","SMSReceiver"+toPhone+" "+smsContent);
            //判断拦截
            if (toPhone.equals("123456789")){
                //拦截广播
                abortBroadcast();
            }
        }
    }
}
