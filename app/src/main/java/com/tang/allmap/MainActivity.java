package com.tang.allmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.tang.allmap.httpvisit.HttpGet;
import com.tang.allmap.mapmanger.LocationManger;
import com.tang.allmap.permissionmanger.PermissionManager;
import com.tang.allmap.weathermanger.WeatherData;

public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    private LocationManger locationManger = null;
    AMap aMap = null;
    public static  String tag = "MainActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        try {
            Thread.sleep(2000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
//获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if(mMapView!=null)
        aMap = mMapView.getMap();
        locationManger = new LocationManger(this,locationChange);
        locationManger.startLocation();

        PermissionManager.initPermission(MainActivity.this,PermissionManager.permissions,0);
        HttpGet.getData(WeatherData.getWeatherNow("深圳市"),
                onHttpGetStringCallBack);
    }

    HttpGet.OnHttpGetStringCallBack onHttpGetStringCallBack = new HttpGet.OnHttpGetStringCallBack() {
        @Override
        public void getData(String data) {

        }
    };



    public void onRequestPermissionsResult(int requestCode, @Nullable  String[] permissions,@Nullable int[] grantResults) {
        if (requestCode == PermissionManager.permissions.length-1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }else{
               /* //彻底关闭整个APP*/
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {

                } else {/*// android2.1*/
                    ActivityManager am = (ActivityManager) MainActivity.this.getSystemService(ACTIVITY_SERVICE);
                    am.restartPackage(MainActivity.this.getPackageName());
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        locationManger.destroyLocaiton();
    }

    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }



    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    LocationManger.LocationChange locationChange = new LocationManger.LocationChange() {

        public void onChange(AMapLocation aMapLocation) {

            Log.e(tag,"-----locationChange-------");
            Log.e(tag,"-----locationChange-------getLongitude="
                    + aMapLocation.getLongitude());
            Log.e(tag,"-----locationChange-------getLatitude="
                    + aMapLocation.getLatitude());

            if (aMap!=null){

                LatLng latLng=new LatLng(22.1,
                       113.6);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                        new CameraPosition(latLng,8,0,0));
                aMap.moveCamera(cameraUpdate);//地图移向指定区域

            }
        }
    };
}
