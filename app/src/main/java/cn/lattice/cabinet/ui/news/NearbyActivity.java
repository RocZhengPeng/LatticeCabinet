package cn.lattice.cabinet.ui.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class NearbyActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby;
    }

    @Override
    protected void initComponents() {
           addSingleTitleBar("附近门店");
    }

}
