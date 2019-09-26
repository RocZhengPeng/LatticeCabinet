package cn.lattice.cabinet.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.login.LoginActivity;
import cn.lattice.cabinet.ui.news.NearbyActivity;
import cn.lattice.cabinet.ui.news.RechargeActivity;
import cn.lattice.cabinet.ui.person.PersonalActivity;

public class MainActivity extends BaseActivity implements AMap.OnMarkerClickListener {

    @BindView(R.id.map_view)
    MapView mMapView;
    //初始化地图控制器对象
    private AMap aMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        // TODO: add setContentView(...) invocation

        //首次进入，判断是否登录
    /*   LoginEntity.DataBean userInfo= DKUserManager.getUserInfo().getData();
       if (userInfo==null){//还没有登录，去登录
           startActivity(new Intent(MainActivity.this,LoginActivity.class));
       }*/


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                LatLng latLng = aMap.getCameraPosition().target;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                aMap.setInfoWindowAdapter(windowAdapter);

                MarkerOptions marker = new MarkerOptions()
                        .position(latLng)
                        .title("测试")
                        .snippet(String.valueOf(1))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.lightning_2));
                aMap.addMarker(marker);
            }
        });
        aMap.setOnMarkerClickListener(this);
        getPersimmion();
    }

    @OnClick({R.id.iv_personal, R.id.iv_menu,R.id.ll_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personal:
                startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                break;
            case R.id.iv_menu:
                startActivity(new Intent(MainActivity.this, NearbyStoresActivity.class));
                break;
            case R.id.ll_scan:
                startActivity(new Intent(MainActivity.this, RechargeActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getPersimmion() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private AMap.InfoWindowAdapter windowAdapter = new AMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            if (infoWindow == null) {
                infoWindow = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.custom_info_window, null);
            }
            render(marker, infoWindow);
            return infoWindow;
//加载custom_info_window.xml布局文件作为InfoWindow的样式
//该布局可在官方Demo布局文件夹下找到
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
//示例没有采用该方法。
        }

        View infoWindow = null;

        /**
         * 自定义infowinfow窗口
         */
        public void render(Marker marker, View view) {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, NearbyActivity.class));
                }
            });

        /*    ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(R.mipmap.car_wash_car);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
               *//* SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.font_level_3)), 0,
                        titleText.length(), 0);
                titleUi.setTextSize(12);
                titleUi.setText(titleText);*//*

            } else {
                titleUi.setText("");
            }*/
        }
    };

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

