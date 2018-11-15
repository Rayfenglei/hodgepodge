package com.example.f2846843.myapplication.GDLocations;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.f2846843.myapplication.R;
import com.example.f2846843.myapplication.utils.MyApplication;

public class GDLocationActivity extends AppCompatActivity implements View.OnClickListener,AMap.OnMyLocationChangeListener,AMap.OnCameraChangeListener{
    private static final String TAG = "GDLocationActivity";
    MapView mapView;
    AMap aMap;
    Button mNormal,mSatellite,mNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdlocation);
        init();
        mapView = findViewById(R.id.gaode_location_mapview);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式

        //显示当前定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位蓝点的定位模式
        myLocationStyle.showMyLocation(true);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.anchor(0.5f, 0.5f);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));//移动缩放
        aMap.setOnCameraChangeListener(this);
        aMap.getUiSettings().setZoomControlsEnabled(true);//缩放控件
        //开启定位
        gaodeLocatoin();
    }


    private void init(){
        mNormal = findViewById(R.id.normal_map);
        mSatellite = findViewById(R.id.satellite_map);
        mNight = findViewById(R.id.night_map);
        mNormal.setOnClickListener(this);
        mSatellite.setOnClickListener(this);
        mNight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.normal_map:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite_map:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.night_map:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                break;
        }
    }
    /*
    * 回调方法获取经纬度信息
    * */
    @Override
    public void onMyLocationChange(Location location) {
        Log.i(TAG,"我的位置 "+location.getLatitude()+" "+location.getLongitude());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    private void gaodeLocatoin(){
        //定位
        GDLocationUtil.getInstance(MyApplication.getContext()).clearInstance();
        GDLocationUtil.getInstance(MyApplication.getContext()).setOnLocationResultListener(new GDLocationUtil.OnLocationResultListener() {
            @Override
            public void onLocationResult(AMapLocation location) {
                //得到定位信息
                Log.i(TAG,"定位信息 "+location.getLatitude()+" "+location.getLongitude());
                GDLocationUtil.getInstance(MyApplication.getContext()).stopLocation();
            }

        });
        GDLocationUtil.getInstance(MyApplication.getContext()).startLocation();
    }


    private void drawMaker(){
        //绘制marker
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.986919,116.353369))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.arrow)))
                .draggable(true));

    }
    private void drawLines(){
        // 绘制曲线
        aMap.addPolyline((new PolylineOptions())
                .add(new LatLng(43.828, 87.621), new LatLng(45.808, 126.55))
                .geodesic(true).color(Color.RED));

    }
}
