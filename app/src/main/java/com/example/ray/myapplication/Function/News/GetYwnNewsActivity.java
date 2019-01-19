package com.example.ray.myapplication.Function.News;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GetYwnNewsActivity extends Activity {

    private static final String TAG = "GetYwnNewsActivity";
    //新闻
    private RecyclerView rvNewsList;
    private ArrayList<AgNewsBean> newsItems = new ArrayList<>();
    private AgNewsAdapter mNewsAdapter;
    private Button btNewChange;
    private AgYwnNewsGet agYwnNewsGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ywn_news);

        initNewsView();
        setNewList();

        //获取数据，解析数据
        agYwnNewsGet.getNewsObservable(new Observer<YwnBean<List<YwnNews>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(YwnBean<List<YwnNews>> listYwnBean) {

                for (int i=0;i<listYwnBean.getData().size();i++){
                    Log.i("getYwnNews",listYwnBean.getData().get(i).getTitle());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"getNewsObservable onError");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //新闻
    private void initNewsView() {
        rvNewsList = findViewById(R.id.rv_ag_news);
        btNewChange = findViewById(R.id.bt_ag_news_change);
        btNewChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agYwnNewsGet.getYwnUid();
            }
        });
    }

    private void setNewList(){
        mNewsAdapter = new AgNewsAdapter(newsItems,this);
        rvNewsList.setLayoutManager(new LinearLayoutManager(this));
        rvNewsList.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnClickItemListener(new AgNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {

                int position = rvNewsList.getChildAdapterPosition(view);

            }
        });
        agYwnNewsGet = new AgYwnNewsGet(this,mNewsAdapter);
        agYwnNewsGet.getYwnUid();
    }


}
