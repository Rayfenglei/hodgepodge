package com.example.f2846843.myapplication.GDLocations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.f2846843.myapplication.R;
import com.example.f2846843.myapplication.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class GDPoiFragment extends Fragment implements PoiSearch.OnPoiSearchListener{

    private EditText mSearchEt;
    private RecyclerView mRecyclerView;
    private GDPoiAdapter adapter;
    private List<GDPoiBean> mData = new ArrayList<>();
    private static final int REQUEST_PERMISSION_LOCATION = 0;
    private String keyWord = "";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch  poiSearch;// POI搜索


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gdpoi_search,container,false);
        mSearchEt = view.findViewById(R.id.gaode_search_et);
        mRecyclerView = view.findViewById(R.id.gaode_search_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        initListener();

        return view;
    }
    public void initListener() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyWord = String.valueOf(charSequence);
                if ("".equals(keyWord)) {
                    Toast.makeText(MyApplication.getContext(), "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    doSearchQuery(keyWord);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void doSearchQuery(String keyWord){
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keyWord, "", "");
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查询页码
        query.setPageNum(0);
        //构造 PoiSearch 对象，并设置监听
        poiSearch = new PoiSearch(MyApplication.getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        //调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        poiSearch.searchPOIAsyn();


    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        //rCode 为1000 时成功,其他为失败
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            // 解析result   获取搜索poi的结果
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    poiResult = result;
                    ArrayList<GDPoiBean> data = new ArrayList<>();//自己创建的数据集合
                    // 取得第一页的poiitem数据，页数从数字0开始
                    //poiResult.getPois()可以获取到PoiItem列表
                    List<PoiItem> poiItems = poiResult.getPois();

                    //若当前城市查询不到所需POI信息，可以通过result.getSearchSuggestionCitys()获取当前Poi搜索的建议城市
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                    //如果搜索关键字明显为误输入，则可通过result.getSearchSuggestionKeywords()方法得到搜索关键词建议。
                    List<String> suggestionKeywords =  poiResult.getSearchSuggestionKeywords();

                    //解析获取到的PoiItem列表
                    for(PoiItem item : poiItems){
                        //获取经纬度对象
                        LatLonPoint llp = item.getLatLonPoint();
                        double lon = llp.getLongitude();
                        double lat = llp.getLatitude();
                        //返回POI的名称
                        String name = item.getTitle();
                        //返回POI的地址
                        String address = item.getSnippet();
                        Log.i("PoiItem", item.getTitle());
                        data.add(new GDPoiBean(address,name,lat,lon));
                    }
                    adapter = new GDPoiAdapter(MyApplication.getContext(), data);
                    mRecyclerView.setAdapter(adapter);
                }
            } else {
                Toast.makeText(MyApplication.getContext(),"无搜索结果",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MyApplication.getContext(),"错误码"+rCode,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

}
