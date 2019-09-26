package cn.lattice.cabinet.ui.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class ProcessingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_processing;
    }

    @Override
    protected void initComponents() {
          addSingleTitleBar("租赁中");
    }
}
