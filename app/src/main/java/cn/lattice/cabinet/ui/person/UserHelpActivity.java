package cn.lattice.cabinet.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;

public class UserHelpActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_help;
    }

    @Override
    protected void initComponents() {
            addSingleTitleBar("使用帮助");
    }
}
