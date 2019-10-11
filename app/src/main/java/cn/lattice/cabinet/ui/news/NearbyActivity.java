package cn.lattice.cabinet.ui.news;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.AllStoreEntity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.ui.DKUserManager;
import cn.lattice.cabinet.ui.FaultActivity;
import cn.lattice.cabinet.ui.MainActivity;
import cn.lattice.cabinet.ui.RentActivity;
import cn.lattice.cabinet.util.DistanceUtil;
import cn.lattice.cabinet.util.SPUtils;
import cn.lattice.cabinet.util.ToastUtils;
import io.reactivex.functions.Consumer;

public class NearbyActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private AllStoreEntity.DataBean dataBean;
    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("附近门店");
        rxPermissions = new RxPermissions(this);
        Bundle bundle = getIntent().getExtras();
        dataBean = (AllStoreEntity.DataBean) bundle.getSerializable("entity");
        tvName.setText(dataBean.getName());
        String lng = (String) SPUtils.get("lng", "0.0");
        String lat = (String) SPUtils.get("lat", "0.0");
        String distance = DistanceUtil.getDistanceTow(Double.valueOf(lng), Double.valueOf(lat), Double.valueOf(dataBean.getAddressLat()), Double.valueOf(dataBean.getAddressLon()));
        tvDistance.setText(distance);
        tvAddressDetail.setText(dataBean.getAddress());
    }

    @OnClick(R.id.btn_rent)
    public void onViewClicked() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(NearbyActivity.this, CaptureActivity.class);
                            startActivityForResult(intent, 1);
                        } else {
                            ToastUtils.showShort("获取权限被拒绝");
                            finish();
                        }
                    }
                });
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
                startActivity(new Intent(NearbyActivity.this, NewLoginActivity.class));
            } else {//已登录
                //判断是否有付押金
                if (userInfo.getCash() == 0) {//没有交押金
                    startActivity(new Intent(NearbyActivity.this, RechargeActivity.class));
                } else {//已经交了押金
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Intent intent = new Intent(NearbyActivity.this, RentActivity.class);
                        intent.putExtra("sn", result);//设备编号
                        startActivity(intent);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(NearbyActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
