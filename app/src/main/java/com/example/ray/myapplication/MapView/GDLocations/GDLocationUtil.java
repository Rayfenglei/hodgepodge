package com.example.ray.myapplication.MapView.GDLocations;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.example.ray.myapplication.utils.Utils;

public class GDLocationUtil {
    private static final String TAG =   "GaoDeLocationUtils";
    private static volatile GDLocationUtil instance = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private static final double EARTH_RADIUS = 6371.0;

    public static GDLocationUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (GDLocationUtil.class) {
                if (instance == null) {
                    instance = new GDLocationUtil(context);
                }
            }
        }
        return instance;
    }

    public void clearInstance () {
        instance = null;
    }

    private GDLocationUtil(Context context) {
        locationClient = new AMapLocationClient(context.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        AMapLocationListener locationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                //得到定位信息
                onLocationResultListener.onLocationResult(location);
                if (null != location) {

                    StringBuilder sb = new StringBuilder();
                    //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                    if (location.getErrorCode() == 0) {
                        sb.append("定位成功" + "\t");
                        sb.append("定位类型: ").append(location.getLocationType()).append("\t");
                        sb.append("经    度    : ").append(location.getLongitude()).append("\t");
                        sb.append("纬    度    : ").append(location.getLatitude()).append("\t");
                        sb.append("精    度    : ").append(location.getAccuracy()).append("米").append("\t");
                        sb.append("提供者    : ").append(location.getProvider()).append("\t");

                        sb.append("速    度    : ").append(location.getSpeed()).append("米/秒").append("\t");
                        sb.append("角    度    : ").append(location.getBearing()).append("\t");
                        // 获取当前提供定位服务的卫星个数
                        sb.append("星    数    : ").append(location.getSatellites()).append("\t");
                        sb.append("国    家    : ").append(location.getCountry()).append("\t");
                        sb.append("省            : ").append(location.getProvince()).append("\t");
                        sb.append("市            : ").append(location.getCity()).append("\t");
                        sb.append("城市编码 : ").append(location.getCityCode()).append("\t");
                        sb.append("区            : ").append(location.getDistrict()).append("\t");
                        sb.append("区域 码   : ").append(location.getAdCode()).append("\t");
                        sb.append("地    址    : ").append(location.getAddress()).append("\t");
                        sb.append("兴趣点    : ").append(location.getPoiName()).append("\t");
                        //定位完成的时间
                        sb.append("定位时间: ").append(Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss")).append("\n");
                    } else {
                        //定位失败
                        sb.append("定位失败" + "\t");
                        sb.append("错误码:").append(location.getErrorCode()).append("\t");
                        sb.append("错误信息:").append(location.getErrorInfo()).append("\t");
                        sb.append("错误描述:").append(location.getLocationDetail()).append("\n");
                    }
                    sb.append("***定位质量报告***").append("\n");
                    sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\t");
                    sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\t");
                    sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\t");
                    sb.append("* 网络类型：").append(location.getLocationQualityReport().getNetworkType()).append("\t");
                    sb.append("* 网络耗时：").append(location.getLocationQualityReport().getNetUseTime()).append("\n");
                    sb.append("****************").append("\n");
                    //定位之后的回调时间
                    sb.append("回调时间: ").append(Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")).append("\t");

                    //解析定位结果，
                    String result = sb.toString();
                    Log.i(TAG, "高德地图:\t\t" + result);
                } else {
                    Log.e(TAG, "定位失败，loc is null");
                }
            }
        };
        locationClient.setLocationListener(locationListener);
    }
    /*
    初始化定位参数
    * */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //高精度定位
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mOption.setHttpTimeOut(30000);
        mOption.setOnceLocation(true);
        return mOption;
    }

    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    public void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    OnLocationResultListener onLocationResultListener = null;

    //重写函数，获得定位信息
    public interface OnLocationResultListener {
        void onLocationResult(AMapLocation location);
    }

    public void setOnLocationResultListener(OnLocationResultListener listener) {
        onLocationResultListener = listener;
    }

    public void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /*
       给定的经度1，纬度1；经度2，纬度2. 计算2个经纬度之间的距离。
       <param name="lat1">经度1</param>
       <param name="lon1">纬度1</param>
       <param name="lat2">经度2</param>
       <param name="lon2">纬度2</param>
       <returns>距离（米）</returns>
       */
    private int calculateDistance(double lat1,double lon1, double lat2,double lon2) {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        lat1 = convertDegreesToRadians(lat1);
        lon1 = convertDegreesToRadians(lon1);
        lat2 = convertDegreesToRadians(lat2);
        lon2 = convertDegreesToRadians(lon2);
        //差值
        double vLon = Math.abs(lon1 - lon2);
        double vLat = Math.abs(lat1 - lat2);
        double h = haverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * haverSin(vLon);
        return (int)(2 * EARTH_RADIUS * Math.asin(Math.sqrt(h)) * 1000);
    }
    /*
    将角度换算为弧度。
    <param name="degrees">角度</param>
    <returns>弧度</returns>*/
    private double convertDegreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private double haverSin(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }
}