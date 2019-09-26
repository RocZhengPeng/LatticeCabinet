package cn.lattice.cabinet.ui.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

/**
 * 订单详情
 */
public class SeeDetailActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_see_detail;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("订单详情");
    }
}
