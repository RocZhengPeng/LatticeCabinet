package cn.lattice.cabinet.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class MyCouponActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("我的优惠券");
    }
}
