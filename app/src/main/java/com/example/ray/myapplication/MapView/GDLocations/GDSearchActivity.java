package com.example.ray.myapplication.MapView.GDLocations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.ray.myapplication.R;

import java.util.List;
import java.util.Vector;

public class GDSearchActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener,Inputtips.InputtipsListener,
        GDSearchAdapter.OnItemClickListener {
    private static final String TAG = "GDSearchActivity";
    private Vector<AddressSearchBean> autoRefreshData = null;
    private Button mBackBtn,mClearBtn;
    private AutoCompleteTextView mSearchTv;
    private RecyclerView mRecyclerView;
    private GDSearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdsearch);
        init();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GDSearchAdapter(this);
        mAdapter.setOnClickItemListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }
    private void init(){
        mBackBtn = findViewById(R.id.gaode_search_back_btn);
        mClearBtn = findViewById(R.id.gaode_search_clear_btn);
        mSearchTv = findViewById(R.id.gaode_search_tv);
        mRecyclerView = findViewById(R.id.gaode_search_list_rv);

        mBackBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
        mSearchTv.addTextChangedListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gaode_search_back_btn:
                startActivity(new Intent(this,GDLocationActivity.class));
                break;
            case R.id.gaode_search_clear_btn:
                mSearchTv.setText("");
                break;
            default:
                break;
        }
    }

    /*
     *    监听text变换
     * */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String newText = charSequence.toString().trim();
        if (!TextUtils.isEmpty(newText)) {
            autoRefreshData = new Vector<>();
            //1、继承 InputtipsListener 监听。
            //2、构造 InputtipsQuery 对象
            InputtipsQuery inputquery = new InputtipsQuery(newText, "深圳");
            inputquery.setCityLimit(true);//限制在当前城市
            //3、构造 Inputtips 对象，并设置监听。
            Inputtips inputTips = new Inputtips(GDSearchActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            //4、调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。返回10条信息
            inputTips.requestInputtipsAsyn();

            //所在城市和全国各获取一次
            InputtipsQuery inputqueryAll = new InputtipsQuery(newText, "");
            inputqueryAll.setCityLimit(true);
            Inputtips inputTipsAll = new Inputtips(GDSearchActivity.this, inputqueryAll);
            inputTipsAll.setInputtipsListener(this);
            inputTipsAll.requestInputtipsAsyn();

        }else {
            if (mAdapter != null) {
                mAdapter.clearAddressData();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (tipList != null && tipList.size() > 0) {
                for (Tip tip : tipList) {
                    if (tip != null && tip.getPoint() != null) {
                        AddressSearchBean bean = new AddressSearchBean();
                        bean.setName(tip.getName());
                        if (TextUtils.isEmpty(tip.getAddress())) {
                            bean.setAddress(tip.getDistrict());
                        } else {
                            bean.setAddress(tip.getAddress());
                        }
                        bean.setCheckedLatlng(new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude()));
                        Log.i(TAG,bean.getName()+"\n");
                        autoRefreshData.add(bean);
                    }
                }
                if (autoRefreshData != null && autoRefreshData.size() > 0) {
                    //添加数据通知改变
                    mAdapter.setAddressData(autoRefreshData);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } else {
            Log.e(TAG, "onGetInputtips : Data Error");
        }
    }

    @Override
    public void onItemClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        Log.i(TAG,"onItemClick "+autoRefreshData.get(position).getName()+"\n");

        finish();
    }
}
