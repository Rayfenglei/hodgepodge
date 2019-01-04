package com.example.ray.myapplication.OkhttpRetrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ray.myapplication.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class OkhttpRetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_retrofit);
        movices();
    }
    private void movices(){

        DemoService demoService = new DemoService();
        MovieService movieService = demoService.getMovieService();
        //调用方法得到一个Call
        Call<ResponseBody> call = movieService.getTop250(0,20);
        //进行网络请求
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try{
                    Log.i("movices:",response.body().string());
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
