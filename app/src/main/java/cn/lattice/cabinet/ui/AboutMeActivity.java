package cn.lattice.cabinet.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class AboutMeActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initComponents() {
          addSingleTitleBar("关于我们");
    }
}
