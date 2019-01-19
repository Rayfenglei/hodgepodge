package com.example.ray.myapplication.Function.News;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgYwnNewsGet  {
    private static final String TAG = "AgYwnNewsGet";
    private Context context;
    private YwnUID uidBean = new YwnUID();
    private AgNewsAdapter newsAdapter;
    private ArrayList<AgNewsBean> agNewsBeans = new ArrayList<>();

    public AgYwnNewsGet(Context context,AgNewsAdapter newsAdapter){
        this.context = context;
        this.newsAdapter = newsAdapter;
    }

    //call
    public void getYwnUid() {

        Call<YwnUID> call =  YwnService.getInstance(context).getService().getUidCall(new HashMap<String, String>());
        call.enqueue(new Callback<YwnUID>() {
            @Override
            public void onResponse(Call<YwnUID> call, Response<YwnUID> response) {
                uidBean = response.body();
                Log.i(TAG, "getUid onResponse  " + response.body().getUid());
                getYwnNews();
            }
            @Override
            public void onFailure(Call<YwnUID> call, Throwable t) {
                Log.i(TAG, "getUid onFailure " + t.getMessage());
            }
        });

    }
    public void getYwnNews(){

        //列表参数
        Map<String, String> param = new HashMap<>();
        param.put("scenario", "0x010101");
        param.put("content_type","0x00000001");
        param.put("link_type","0x00000003");
        param.put("uid", uidBean.getUid());
        param.put("operation", "2");
        param.put("count", "15");
        param.put("display", "0x00000002");

        Call<YwnBean<List<YwnNews>>> call = YwnService.getInstance(context).getService().getNormalNewsCall(param);
        call.enqueue(new Callback<YwnBean<List<YwnNews>>>() {
            @Override
            public void onResponse(Call<YwnBean<List<YwnNews>>> call, Response<YwnBean<List<YwnNews>>> response) {
                agNewsBeans.clear();
                Log.i(TAG,"getYwnNews size "+response.body().getData().size());

                for (int i=0;i<response.body().getData().size();i++){
                    AgNewsBean newsBean = new AgNewsBean();
                    newsBean.setTitle(response.body().getData().get(i).getTitle());
                    newsBean.setAuthor(response.body().getData().get(i).getSource());
                    newsBean.setTime(timeStamp2Date(response.body().getData().get(i).getPublish_time()));
                    newsBean.setImageUrl(response.body().getData().get(i).getList_images().get(0).getImg_url());
                    newsBean.setContentUrl(response.body().getData().get(i).getOrigin_url());
                    Log.i(TAG,newsBean.toString());
                    agNewsBeans.add(newsBean);
                }
                newsAdapter.updateData(agNewsBeans);
            }
            @Override
            public void onFailure(Call<YwnBean<List<YwnNews>>> call, Throwable t) {
                Log.i(TAG, "getYwnNews onFailure " + t.getMessage());
            }
        });
    }
    //second to date
    private String timeStamp2Date(String time) {
        String format = "MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(time)*1000L));
    }

    //Observable

    public void getNewsObservable(Observer<YwnBean<List<YwnNews>>> observer){

        //apply 将Observable<YwnUID> 合并到Observable<YwnBean<List<YwnNews>>> 并且使用YwnUID数据
        Observable newsObservable = getUidObservable().flatMap(new Function<YwnUID, ObservableSource<YwnBean<List<YwnNews>>>>() {
            @Override
            public ObservableSource<YwnBean<List<YwnNews>>> apply(YwnUID uidBean) throws Exception {

                Map<String, String> param = new HashMap<>();
                param.put("scenario", "0x010101");
                param.put("content_type","0x00000001");
                param.put("link_type","0x00000003");
                param.put("uid", "02011901071554560001213746494202");
                param.put("operation", "2");
                param.put("count", "15");
                param.put("display", "0x00000002");

                //返回的是Observable<YwnBean<List<YwnNews>>> 类型
                return getNormalNews(param).retryWhen(new RetryWithDelay(3,1000));
            }
        });

        //注册订阅事件
        newsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //获取getNormalNewsObservable
    private Observable<YwnBean<List<YwnNews>>> getNormalNews(Map<String, String> map){
        Log.i(TAG," getNormalNews");
        return  YwnService.getInstance(context).getService().getNormalNewsObservable(map) ;
    }

    //获取getUidObservable
    private Observable<YwnUID> getUidObservable(){
        Log.i(TAG," getUidObservable");
        Observable<YwnUID> uidObservable ;
        uidObservable = YwnService.getInstance(context).getService().getUidObservable(new HashMap<String, String>())
                .doOnNext(new Consumer<YwnUID>() {
                    @Override
                    public void accept(YwnUID ywnUID) throws Exception {
                        Log.i(TAG," "+ywnUID.getUid());
                    }
                }).retryWhen(new RetryWithDelay(3,1000));

//        uidObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<YwnUID>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {}
//                    @Override
//                    public void onNext(YwnUID ywnUID) {
//                        Log.i("uidObservable"," "+ywnUID.getUid());
//                    }
//                    @Override
//                    public void onError(Throwable e) {}
//                    @Override
//                    public void onComplete() {}
//                });
        return uidObservable;
    }
}
