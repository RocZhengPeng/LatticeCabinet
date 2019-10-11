package cn.lattice.cabinet.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class AboutMeTowActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me_tow;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar(getString(R.string.about_us));
    }
}
