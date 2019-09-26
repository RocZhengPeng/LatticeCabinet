package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.ui.news.ProcessingActivity;

public class RentActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("租赁充电宝");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_rent)
    public void onViewClicked() {
        startActivity(new Intent(RentActivity.this, ProcessingActivity.class));
    }
}
