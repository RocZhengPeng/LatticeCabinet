package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.ui.news.ProcessingActivity;
import cn.lattice.cabinet.util.ToastUtils;
import okhttp3.ResponseBody;

public class RentActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("租赁充电宝");
        String sn = getIntent().getStringExtra("sn");
        LoginEntity.DataBean userInfo = DKUserManager.getUserInfo();
        int userID = userInfo.getId();
        RetrofitClient.getInstance(RocApplication.getContext()).deviceOpen(sn,userID, new AbstractDialogSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    ToastUtils.showShort(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.btn_rent)
    public void onViewClicked() {
        startActivity(new Intent(RentActivity.this, ProcessingActivity.class));
    }
}
