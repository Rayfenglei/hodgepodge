package com.example.ray.myapplication.News;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IywnService {

    //http://api.inveno.com/gate/info/list
    @FormUrlEncoded
    @POST("/gate/api/list")
    Call<YwnBean<List<YwnNews>>> getNormalNewsCall(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/gate/getuid")
    Call<YwnUID> getUidCall(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("/gate/api/list")
    Observable<YwnBean<List<YwnNews>>> getNormalNewsObservable(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/gate/getuid")
    Observable<YwnUID> getUidObservable(@FieldMap Map<String, String> map);
}
