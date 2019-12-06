package com.tang.allmap.mapmanger;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationManger {

    public static final String tag = "LocationManger";
    public static boolean   isDebuge = true;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public Context mContext = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public  AMapLocation mAMapLocation = null;
    public LocationChange mLocationChange = null;

    public LocationManger(Context context,LocationChange locationChange){

        mContext = context;
        mAMapLocation = null;
        if(mLocationClient==null)
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        if(mLocationOption==null)
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        if (isDebuge)
            Log.e(tag,"----LocationManger--init--");
        mLocationChange = locationChange;
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (isDebuge)
                Log.e(tag,"----onLocationChanged----");
            String address = aMapLocation.getAddress();

            Log.e(tag,"-----locationChange-------address=" + address);

            Log.e(tag,"-----locationChange-------getLatitude="
                    + aMapLocation.getLatitude());
            Log.e(tag,"-----locationChange-------getLongitude="
                    + aMapLocation.getLongitude());
            if (mAMapLocation==null)
                mAMapLocation = aMapLocation;
            if (mLocationChange!=null)
            mLocationChange.onChange(mAMapLocation);
        }
    };

    public void startLocation(){
        if (isDebuge)
            Log.e(tag,"----startLocation----");
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    public void stopLocaiton(){

        if (isDebuge)
            Log.e(tag,"----stopLocaiton----");
        if(null != mLocationClient){
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
        }
    }

    public void destroyLocaiton(){

        stopLocaiton();
        if (isDebuge)
            Log.e(tag,"----destroyLocaiton----");
        if(null != mLocationClient){
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.onDestroy();
        }
        mAMapLocation = null;
    }

    public interface  LocationChange{
        void onChange(AMapLocation aMapLocation);
    };
}
