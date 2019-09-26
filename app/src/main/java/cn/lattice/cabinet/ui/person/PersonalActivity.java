package cn.lattice.cabinet.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.ui.FaultActivity;
import cn.lattice.cabinet.ui.news.InputMoneyActivity;
import cn.lattice.cabinet.ui.news.NewLoginActivity;

public class PersonalActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal2;
    }

    @Override
    protected void initComponents() {
        ButterKnife.bind(this);
        addSingleTitleBar("个人中心");
    }

    @OnClick({R.id.tv_my_order, R.id.tv_coupon, R.id.tv_fix, R.id.tv_help, R.id.tv_about, R.id.tv_sign_out, R.id.ll_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_order:
                startActivity(new Intent(PersonalActivity.this, MyOrderActivity.class));
                break;
            case R.id.tv_coupon:
                startActivity(new Intent(PersonalActivity.this, MyCouponActivity.class));
                break;
            case R.id.tv_fix:
                startActivity(new Intent(PersonalActivity.this, FaultActivity.class));
                break;
            case R.id.tv_help:
                break;
            case R.id.tv_about:
                startActivity(new Intent(PersonalActivity.this, AboutMeTowActivity.class));
                break;
            case R.id.tv_sign_out:
                startActivity(new Intent(PersonalActivity.this, NewLoginActivity.class));
                break;
            case R.id.ll_money:
                startActivity(new Intent(PersonalActivity.this, InputMoneyActivity.class));
                break;
        }
    }
}
