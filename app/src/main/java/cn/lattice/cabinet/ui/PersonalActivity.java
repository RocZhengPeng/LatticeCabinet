package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.login.LoginActivity;
import cn.lattice.cabinet.util.ActivityManager;

public class PersonalActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("个人中心");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_report, R.id.tv_fault, R.id.tv_help, R.id.tv_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_report:
                startActivity(new Intent(PersonalActivity.this, MyReportActivity.class));
                break;
            case R.id.tv_fault:
                startActivity(new Intent(PersonalActivity.this, FaultActivity.class));
                break;
            case R.id.tv_help:
                break;
            case R.id.tv_about:
                startActivity(new Intent(PersonalActivity.this, AboutMeActivity.class));
                break;
            case R.id.tv_sign_out:
                startActivity(new Intent(PersonalActivity.this, LoginActivity.class));
                ActivityManager.getInstance().finishAllActivity();
                break;
        }
    }
}
