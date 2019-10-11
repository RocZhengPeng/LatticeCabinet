package cn.lattice.cabinet.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.ui.DKUserManager;
import cn.lattice.cabinet.ui.MainActivity;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.ui.RocApplication;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.util.ToastUtils;
import okhttp3.ResponseBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_registered, R.id.tv_forget, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_registered:
                startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.btn_login:
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                RetrofitClient.getInstance(RocApplication.getContext()).login(phone, password, new AbstractDialogSubscriber<ResponseBody>(this) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            int state = jsonObject.getInt("status");
                            if (state == 0) {//成功
                                //保存登录信息

                                Gson gson = new Gson();
                                LoginEntity loginEntity = gson.fromJson(responseBody.toString(), LoginEntity.class);

                                //保存登录信息
                                DKUserManager.saveUserInfo(loginEntity.getData());

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            } else {//失败
                                String message = jsonObject.getString("msg");
                                ToastUtils.showShort(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}
