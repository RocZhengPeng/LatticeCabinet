package cn.lattice.cabinet.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.AllNetEntity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.login.LoginActivity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.ui.news.NewLoginActivity;
import cn.lattice.cabinet.ui.news.RechargeActivity;
import cn.lattice.cabinet.ui.person.PersonalActivity;
import cn.lattice.cabinet.util.SPUtils;
import cn.lattice.cabinet.util.ToastUtils;
import io.reactivex.functions.Consumer;

// AMap.OnMarkerClickListener
public class MainActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap.InfoWindowAdapter windowAdapter;

    private GoogleMap mMap;

    private LocationTracker locationTrackObj;

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private View infoWindow = null;
    private List<AllNetEntity.DataBean> beanList = new ArrayList<>();//附近所有的店铺列表

    private RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        //首次进入，判断是否登录

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationTrackObj = new LocationTracker(this);
        if (!locationTrackObj.canGetLocation()) {
            locationTrackObj.showSettingsAlert();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
        }
    }

    /**
     * 初始化地图
     *
     * @param mMap
     */
    private void initializeMap(GoogleMap mMap) {
        if (mMap != null) {
            windowAdapter = new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    if (infoWindow == null) {
                        infoWindow = LayoutInflater.from(MainActivity.this).inflate(
                                R.layout.custom_info_window, null);
                    }
                    render(marker, infoWindow);
                    return infoWindow;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    if (infoWindow == null) {
                        infoWindow = LayoutInflater.from(MainActivity.this).inflate(
                                R.layout.custom_info_window, null);
                    }
                    render(marker, infoWindow);
                    return infoWindow;
                }
            };

            mMap.setInfoWindowAdapter(windowAdapter);
            mMap.setOnInfoWindowClickListener(marker -> {

              /*  startActivity(new Intent(GoogleMapsMerchantActivity.this, MerchantDetailsActivity.class)
                        .putExtra(MerchantDetailsActivity.HOTELUUID, entity.getUuid()));*/
            });

            mMap.setOnMarkerClickListener(marker -> {
               /* if (entity != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.disallowHardwareConfig();
                    Glide.with(context)
                            .applyDefaultRequestOptions(requestOptions)
                            .load(PicUtils.getUrl(entity.getImg_list()))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    drawable = resource;
                                    marker.showInfoWindow();
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
                                }
                            });
                }*/
                marker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
                return true;
            });

            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            try {
                Location location = mMap.getMyLocation();
                if (location == null)
                    location = locationTrackObj.getLocation();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                        LatLng(location.getLatitude(),
                        location.getLongitude()), 16));

                SPUtils.put("lat", String.valueOf(location.getLatitude()));
                SPUtils.put("lng", String.valueOf(location.getLongitude()));
                requestData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        ImageView ivLogo = view.findViewById(R.id.iv_image);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvAddress = view.findViewById(R.id.tv_address_detail);
        TextView tvDistance = view.findViewById(R.id.tv_distance);

        int position = Integer.valueOf(marker.getSnippet());
        AllNetEntity.DataBean dataBea = beanList.get(position);
        Glide.with(this).load(dataBea.getLogo()).into(ivLogo);
        tvName.setText(dataBea.getName());
        tvAddress.setText(dataBea.getAddress());
        tvDistance.setText(tvDistance.getText());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            LoginEntity.DataBean userInfo = DKUserManager.getUserInfo();
            if (!DKUserManager.isLogined()) {//还没有登录，去登录
                startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
            } else {//已登录
                //判断是否有付押金
              /*  if (userInfo.getCash() == 0) {//没有交押金
                    startActivity(new Intent(MainActivity.this, RechargeActivity.class));
                } else {//已经交了押金*/
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Intent intent = new Intent(MainActivity.this, RentActivity.class);
                        intent.putExtra("sn", result);//设备编号
                        startActivity(intent);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }

              //  }
            }
        } else if (requestCode == 2) {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }

            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeMap(mMap);
                }
                return;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                initializeMap(mMap);
            }
        } else {
            initializeMap(mMap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_personal, R.id.iv_menu, R.id.ll_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personal:
                if (!DKUserManager.isLogined()) {//还没有登录，去登录
                    startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                }
                break;
            case R.id.iv_menu:
                startActivity(new Intent(MainActivity.this, NearbyStoresActivity.class));
                break;
            case R.id.ll_scan:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //如果应用之前请求过此权限但用户拒绝了请求，shouldShowRequestPermissionRationale将返回 true。
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA)) {

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, 2);
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    private class LocationTracker implements LocationListener {

        private final Context mContext;

        // flag for GPS status
        private boolean isGPSEnabled = false;

        // flag for network status
        private boolean isNetworkEnabled = false;

        // flag for GPS status
        private boolean canGetLocation = false;

        private Location location; // location
        private double latitude; // latitude
        private double longitude; // longitude

        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000; // 1 sec

        private final String TAG = "LocationTracker";
        // Declaring a Location Manager
        protected LocationManager locationManager;

        public LocationTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                    this.canGetLocation = false;
                } else {
                    this.canGetLocation = true;
                    // First get location from Network Provider
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

            return location;
        }

        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(LocationTracker.this);
            }
        }

        /**
         * Function to get latitude
         */
        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }

        /**
         * Function to get longitude
         */
        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }

        /**
         * Function to check GPS/wifi enabled
         *
         * @return boolean
         */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        /**
         * Function to show settings alert dialog
         * On pressing Settings button will lauch Settings Options
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialog.setTitle("GPS Settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Click on setting to enable and get location, please start app again after turning on GPS.");
            alertDialog.setCancelable(false);

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });


            // Showing Alert Message
            alertDialog.show();
        }


        @Override
        public void onLocationChanged(Location location) {
            //            mapRipple.withNumberOfRipples(3);
            this.location = location;
//            Toast.makeText(context, "  " + location.getLatitude() + ",  " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            location = getLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void requestData(String lat, String lng) {
        RetrofitClient.getInstance(RocApplication.getContext()).selectAllNet(lat, lng, new AbstractDialogSubscriber<AllNetEntity>(MainActivity.this) {
            @Override
            public void onNext(AllNetEntity allNetEntity) {
                beanList.clear();
                beanList.addAll(allNetEntity.getData());
                for (int i = 0; i < beanList.size(); i++) {
                    AllNetEntity.DataBean dataBean = beanList.get(i);
                    LatLng latLng = new LatLng(Double.valueOf(dataBean.getAddressLon()), Double.valueOf(dataBean.getAddressLat()));
                    MarkerOptions marker = new MarkerOptions()
                            .position(latLng)
                            .title(dataBean.getName())
                            .snippet(String.valueOf(i))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.lightning));
                    mMap.addMarker(marker);
                }
            }
        });
    }
}

