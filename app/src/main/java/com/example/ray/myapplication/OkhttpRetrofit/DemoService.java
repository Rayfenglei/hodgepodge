package com.example.ray.myapplication.OkhttpRetrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoService {
    public static  String basu_url = "https://api.douban.com/v2/movie/";

    public DemoService(){

    }
    public MovieService getMovieService(){
        //OkHttpClient实例 和配置
        OkHttpClient client = new  OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(5, TimeUnit.SECONDS)//设置读取超时时间
                .build();
        //创建一个Retrofit 实例，并且完成相关的配置
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(basu_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(MovieService.class);
    }


}