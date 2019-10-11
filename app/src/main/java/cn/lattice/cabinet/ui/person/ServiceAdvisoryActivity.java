package cn.lattice.cabinet.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class ServiceAdvisoryActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_advisory;
    }

    @Override
    protected void initComponents() {
       addSingleTitleBar("客服咨询");
    }
}
