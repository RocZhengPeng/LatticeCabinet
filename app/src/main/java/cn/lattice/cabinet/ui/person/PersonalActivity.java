package cn.lattice.cabinet.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.ui.DKUserManager;
import cn.lattice.cabinet.ui.FaultActivity;
import cn.lattice.cabinet.ui.news.InputMoneyActivity;
import cn.lattice.cabinet.ui.news.NewLoginActivity;
import cn.lattice.cabinet.util.DKCallBackBoolean;
import cn.lattice.cabinet.util.DialogUtil;

public class PersonalActivity extends BaseActivity {


    @BindView(R.id.tv_user_head)
    ImageView tvUserHead;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal2;
    }

    @Override
    protected void initComponents() {
        ButterKnife.bind(this);
        addSingleTitleBar("个人中心");

        LoginEntity.DataBean userInfo = DKUserManager.getUserInfo();
        if (userInfo != null) {
            tvName.setText(userInfo.getPhoneNumber());
        }
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
                startActivity(new Intent(PersonalActivity.this, UserHelpActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(PersonalActivity.this, AboutMeTowActivity.class));
                break;
            case R.id.tv_sign_out:
                DialogUtil.getInstance().showDialog(this, "是否确定退出登录？", new DKCallBackBoolean() {

                    @Override
                    public void action(int type) {
                        DKUserManager.resetUserInfo();
                        startActivity(new Intent(PersonalActivity.this, NewLoginActivity.class));
                    }
                }, true);

                break;
            case R.id.ll_money:
                startActivity(new Intent(PersonalActivity.this, InputMoneyActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
