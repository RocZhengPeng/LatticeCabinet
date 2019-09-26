package cn.lattice.cabinet.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.ui.RentActivity;
import cn.lattice.cabinet.widget.CustomDialog;

public class RechargeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("Recharge Deposit");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        CustomDialog customDialog = new CustomDialog(this, R.layout.dialog_layout_complete, new int[]{R.id.tv_know});
        customDialog.setOnCenterItemClickListener((dialog, view) -> {
            startActivity(new Intent(RechargeActivity.this, RentActivity.class));
        });
        customDialog.show();
    }
}
