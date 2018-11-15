package com.example.f2846843.myapplication.HttpConnect;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/*
* 参考文章 https://www.jianshu.com/p/da4a806e599b
* */

/*
    AsyncTask参数
    第一个：传入后台任务的参数 doInBackground(String)
    第二个：作为进度显示的单位 publishProgress(Integer) --> onProgressUpdate(Integer)
    第三个：表示反馈执行结果 doInBackground(String).return Integer --> onPostExecute(Integer)
*/
public class OKhttp extends AsyncTask<String,Integer,Integer> {

    private static final String TAG = OKhttp.class.getSimpleName();


    public OKhttp(){}

    @Override
    protected Integer doInBackground(String... strings) {

        try{
            String url = "www.baidu.com";
            OkHttpClient okHttpClientGet =new OkHttpClient();

            //GET
            Request requestGet = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            //1.GET
            Response response = okHttpClientGet.newCall(requestGet).execute();
            String data = response.body().string();

            final Call call = okHttpClientGet.newCall(requestGet);
            //2.异步get
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG,"onFailure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string(); //获取返回的数据
                }
            });

            //3.同步get
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = call.execute();
                        String data = response.body().string();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //POST
            OkHttpClient okHttpClientPost = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");//传递的数据类型

            //任何类型
            RequestBody requestPostString = new FormBody.Builder()
                    .add("username","haha")
                    .build();
            Request requestPostS = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(requestPostString)
                    .build();

            //提交String数据
            //1
            String requestBodyString = "I am Jdqm.";
            Request requestPost = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(RequestBody.create(mediaType, requestBodyString))
                    .build();



            // 提交流
            RequestBody requestBody = new RequestBody() {
                @Nullable
                @Override
                public MediaType contentType() { return MediaType.parse("text/x-markdown; charset=utf-8"); }
                @Override
                public void writeTo(BufferedSink sink) throws IOException { sink.writeUtf8("I am Jdqm."); }
            };
            Request requestStream = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(requestBody)
                    .build();

            //提交文件
            File file = new File("test.md");
            Request requestFile = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(RequestBody.create(mediaType, file))
                    .build();

            /*
            * okHttpClientPost.newCall(requestStream).enqueue(new Callback(){}) 提交流
            * okHttpClientPost.newCall(requestFile).enqueue(new Callback(){}) 提交文件
            *
            */

            okHttpClientPost.newCall(requestPost).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                    Headers headers = response.headers(); //
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                    }
                    Log.d(TAG, "onResponse: " + response.body().string());
                }
            });


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }





}
