package com.example.ray.myapplication.Function.News;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.ray.myapplication.BuildConfig;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class YwnService {
    //Browser
    private static final String TAG = "YwnService";
    //设缓存有效期为1天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 ;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static final String BASE_URL = "http://iai.inveno.com";
    private static YwnService sInstance;
    private Context mContext;
    private IywnService mService;


    private YwnService(Context context) {
        mContext = context.getApplicationContext();
    }

    public static YwnService getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new YwnService(context);
        }
        return sInstance;
    }

    public synchronized IywnService getService(){
        if(mService == null){
            Log.i(TAG, "IywnService getService ");
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(sCommonParamInterceptor)
                    .addInterceptor(sLoggingInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            mService = retrofit.create(IywnService.class);
        }
        return mService;
    }

    /**
     * 添加公共参数
     */
    private final Interceptor sCommonParamInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.i(TAG, "add sCommonParamInterceptor ");

            String time = String.valueOf(System.currentTimeMillis()/1000);
            String data = null;
            Request oldRequest = chain.request();
            Request.Builder requestBuilder = oldRequest.newBuilder();
            if(oldRequest.body() instanceof FormBody){
                FormBody.Builder newFormBody = new FormBody.Builder();

                FormBody oldFormBody = (FormBody) oldRequest.body();
                for (int i = 0;i<oldFormBody.size();i++){
                    if(oldFormBody.encodedName(i).equals("data")){
                        data = oldFormBody.value(i);
                    }
                    if(oldFormBody.encodedName(i).equals("report_time")){
                        time = oldFormBody.value(i);
                    }
                    newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }


                String network = String.valueOf(getCurrentNetType(mContext));
                newFormBody.addEncoded("product_id","")
                        .addEncoded("promotion","")
                        .addEncoded("request_time", time)
                        .addEncoded("app_ver", BuildConfig.VERSION_NAME)
                        .addEncoded("api_ver", "3.0.0")
                        .addEncoded("tk", ApiUtils.getCommonTK(data,time))
                        .addEncoded("network", network)
                        .addEncoded("imei", ApiUtils.getImei(mContext))
                        .addEncoded("aid", ApiUtils.getAndroidId(mContext))
                        .addEncoded("brand", Build.BRAND)
                        .addEncoded("model", Build.MODEL)
                        .addEncoded("platform", "android")
                        .addEncoded("app_lan", "zh_CN");
                requestBuilder.method(oldRequest.method(),newFormBody.build());
            }

            Request request = requestBuilder.build();
            return chain.proceed(request);

        }
    };


    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.i(TAG, "add sLoggingInterceptor ");
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
                Log.d("LogTAG", "url = " + request.url() + "\nparam = " + _parseParams(request.body(), requestBuffer));
            } else {
                Log.d("LogTAG", "request.body() == null");
            }
            final Response response = chain.proceed(request);
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    public static int getCurrentNetType(Context context) {
        int result = 8;
        int netType = -1;
        int subType = -1;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                netType = info.getType();
                subType = info.getSubtype();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    result = 1;//wifi
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                            || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                        result = 2;//2g
                    } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                            || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                            || subType == TelephonyManager.NETWORK_TYPE_EVDO_B || subType == TelephonyManager.NETWORK_TYPE_HSPA
                            || subType == TelephonyManager.NETWORK_TYPE_HSPAP) {
                        result = 3;//3g
                    } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                        result = 4;//4g
                    } else {
                        result = 6;//其他移动网络
                    }
                } else {
                    result = 7;//其他网络
                }
            }
        }
        Log.i(TAG, "getCurrentNetType " + Arrays.asList(netType, subType, result));
        return result;
    }
}
