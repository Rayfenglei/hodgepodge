package com.example.ray.myapplication.UIView.StickyRecycler;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ray.collection.Sticky.library.PowerfulStickyDecoration;
import com.example.ray.collection.Sticky.library.StickyDecoration;
import com.example.ray.collection.Sticky.library.listener.GroupListener;
import com.example.ray.collection.Sticky.library.listener.OnGroupClickListener;
import com.example.ray.collection.Sticky.library.listener.PowerGroupListener;
import com.example.ray.collection.Sticky.library.util.DensityUtil;
import com.example.ray.myapplication.R;

import java.util.ArrayList;

public class StickyRecyclerActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private StickyRecyclerAdapter mRa;
    private Button mRvBt;
    private ArrayList<CityBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recycler);
        initView();
        //TextSticky();
        ImageSticky();
    }

    private void initView(){
        mRv = findViewById(R.id.text_recycler_view);
        mRvBt = findViewById(R.id.text_sticky_bt);
    }

    /*
    * 自定义悬浮Item
    * */
    private void ImageSticky(){
        list = getCityList();

        //------------- PowerfulStickyDecoration 使用部分  ----------------
        PowerGroupListener listener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取组名，用于判断是否是同一组
                if (list.size() > position && position > -1) {
                    return list.get(position).getProvince();
                }
                return null;
            }

            @Override
            public View getGroupView(int position) {
                //获取自定定义的组View
                if (list.size() > position && position > -1) {
                    View view = getLayoutInflater().inflate(R.layout.image_sticky_item, null, false); //R.layout.item_group 自定义布局
                    ((TextView) view.findViewById(R.id.image_sticky_tv)).setText(list.get(position).getProvince());
                    return view;
                } else {
                    return null;
                }
            }
        };
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(listener)
                .setGroupHeight(DensityUtil.dip2px(this, 40))     //设置高度
                .setGroupBackground(Color.parseColor("#48BDFF"))        //设置背景
                .setDivideColor(Color.parseColor("#CCCCCC"))            //分割线颜色
                .setDivideHeight(DensityUtil.dip2px(this, 1))     //分割线高度
                .setCacheEnable(true)                                              //是否使用缓存
               // .setHeaderCount(0)                                                  //头部数量
                .setOnClickListener(new OnGroupClickListener() {                   //点击事件，返回当前分组下的第一个item的position
                    @Override
                    public void onClick(int position, int id) {                                 //Group点击事件
                        String content = "onGroupClick --> " + list.get(position).getProvince() + "   id --> " + id;
                        Toast.makeText(StickyRecyclerActivity.this, content, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        //-------------PowerfulStickyDecoration----------------
        //RV布局管理
//        RecyclerView.LayoutManager manager;
//
//        网格布局时需要这样使用
//        manager = new GridLayoutManager(this, 3);
//        decoration.resetSpan(mRv, (GridLayoutManager) manager);
//        mRv.setLayoutManager(manager);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        //Item动画
        mRv.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRv.addItemDecoration(decoration);
        mRa = new StickyRecyclerAdapter(this,list);
        mRv.setAdapter(mRa);

    }

    /*
    * 文字悬浮Item
    * */
    private void TextSticky(){
        list = getCityList();
        //------------- StickyDecoration 使用部分  ----------------
        StickyDecoration decoration = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (list.size() > position && position > -1) {
                            //获取组名，用于判断是否是同一组
                            return list.get(position).getProvince();
                        }
                        return null;
                    }
                })
                .setGroupBackground(Color.parseColor("#48BDFF"))        //背景色
                .setGroupHeight(DensityUtil.dip2px(this, 35))     //高度
                .setDivideColor(Color.parseColor("#EE96BC"))            //分割线颜色
                .setDivideHeight(DensityUtil.dip2px(this, 2))     //分割线高度 (默认没有分割线)
                .setGroupTextColor(Color.BLACK)                                    //字体颜色 （默认）
                .setGroupTextSize(DensityUtil.sp2px(this, 15))    //字体大小
                .setTextSideMargin(DensityUtil.dip2px(this, 10))  // 边距   靠左时为左边距  靠右时为右边距
                //.setHeaderCount(2)                                             // header数量（默认0）
                .setOnClickListener(new OnGroupClickListener() {                   //点击事件，返回当前分组下的第一个item的position
                    @Override
                    public void onClick(int position, int id) {                                 //Group点击事件
                        String content = "onGroupClick --> " + list.get(position).getProvince() ;
                        Toast.makeText(StickyRecyclerActivity.this, content, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        //------------- StickyDecoration 使用部分  ----------------
        //RV布局管理
//        RecyclerView.LayoutManager manager;
//
//        网格布局时需要这样使用
//        manager = new GridLayoutManager(this, 3);
//        decoration.resetSpan(mRv, (GridLayoutManager) manager);
//        mRv.setLayoutManager(manager);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        //Item动画
        mRv.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRv.addItemDecoration(decoration);
        mRa = new StickyRecyclerAdapter(this,list);
        mRv.setAdapter(mRa);

    }

    public static ArrayList<CityBean> getCityList() {
        String[] CITYS = {"福建省", "安徽省", "浙江省", "江苏省"};

        ArrayList<CityBean> dataList = new ArrayList<>();
        final String FU_JIAN = CITYS[0];

        dataList.add(new CityBean("福州", FU_JIAN));
        dataList.add(new CityBean("厦门", FU_JIAN));
        dataList.add(new CityBean("泉州", FU_JIAN));
        dataList.add(new CityBean("宁德", FU_JIAN));
        dataList.add(new CityBean("漳州", FU_JIAN));
        dataList.add(new CityBean("福州", FU_JIAN));
        dataList.add(new CityBean("厦门", FU_JIAN));
        dataList.add(new CityBean("泉州", FU_JIAN));
        dataList.add(new CityBean("宁德", FU_JIAN));
        dataList.add(new CityBean("漳州", FU_JIAN));dataList.add(new CityBean("福州", FU_JIAN));
        dataList.add(new CityBean("厦门", FU_JIAN));
        dataList.add(new CityBean("泉州", FU_JIAN));
        dataList.add(new CityBean("宁德", FU_JIAN));
        dataList.add(new CityBean("漳州", FU_JIAN));
        final String AN_HUI = CITYS[1];
        dataList.add(new CityBean("合肥", AN_HUI));
        dataList.add(new CityBean("芜湖", AN_HUI));
        dataList.add(new CityBean("蚌埠", AN_HUI));
        final String ZHE_JIANG = CITYS[2];
        dataList.add(new CityBean("杭州", ZHE_JIANG));
        dataList.add(new CityBean("宁波", ZHE_JIANG));
        dataList.add(new CityBean("温州", ZHE_JIANG));
        dataList.add(new CityBean("嘉兴", ZHE_JIANG));
        dataList.add(new CityBean("绍兴", ZHE_JIANG));
        dataList.add(new CityBean("金华", ZHE_JIANG));
        dataList.add(new CityBean("湖州", ZHE_JIANG));
        dataList.add(new CityBean("舟山", ZHE_JIANG));
        dataList.add(new CityBean("杭州", ZHE_JIANG));
        dataList.add(new CityBean("宁波", ZHE_JIANG));
        dataList.add(new CityBean("温州", ZHE_JIANG));
        dataList.add(new CityBean("嘉兴", ZHE_JIANG));
        dataList.add(new CityBean("绍兴", ZHE_JIANG));
        dataList.add(new CityBean("金华", ZHE_JIANG));
        dataList.add(new CityBean("湖州", ZHE_JIANG));
        dataList.add(new CityBean("舟山", ZHE_JIANG)); dataList.add(new CityBean("杭州", ZHE_JIANG));
        dataList.add(new CityBean("宁波", ZHE_JIANG));
        dataList.add(new CityBean("温州", ZHE_JIANG));
        dataList.add(new CityBean("嘉兴", ZHE_JIANG));
        dataList.add(new CityBean("绍兴", ZHE_JIANG));
        dataList.add(new CityBean("金华", ZHE_JIANG));
        dataList.add(new CityBean("湖州", ZHE_JIANG));
        dataList.add(new CityBean("舟山", ZHE_JIANG));
        final String JIANG_SU = CITYS[3];
        dataList.add(new CityBean("南京", JIANG_SU));
        return dataList;
    }

}
