package cn.lattice.cabinet.ui.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.common.sms.SmsCodeImpl;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.login.LoginActivity;
import cn.lattice.cabinet.login.RegisteredActivity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.ui.DKUserManager;
import cn.lattice.cabinet.ui.MainActivity;
import cn.lattice.cabinet.ui.RocApplication;
import cn.lattice.cabinet.util.ToastUtils;
import okhttp3.ResponseBody;

public class NewLoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;
    private List<String> phoneCodeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_login;
    }

    @Override
    protected void initComponents() {

    }

    @OnClick({R.id.btn_login, R.id.tv_registered, R.id.tv_phone_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                RetrofitClient.getInstance(RocApplication.getContext()).login(phone, password, new AbstractDialogSubscriber<ResponseBody>(this) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string();
                            JSONObject jsonObject = new JSONObject(json);
                            int state = jsonObject.getInt("status");
                            if (state == 1) {//成功
                                //保存登录信息
                                Gson gson = new Gson();
                                LoginEntity loginEntity = gson.fromJson(json, LoginEntity.class);
                                //保存登录信息
                                DKUserManager.saveUserInfo(loginEntity.getData());

                                startActivity(new Intent(NewLoginActivity.this, MainActivity.class));
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
            case R.id.tv_registered:
                startActivity(new Intent(NewLoginActivity.this, RegisteredActivity.class));
                break;
            case R.id.tv_phone_code:
                showPhoneTypeWindows();
                break;
        }
    }

    private void showPhoneTypeWindows() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvPhoneCode.setText(phoneCodeList.get(options1));
            }
        }).setTitleText("选择手机号地区")
                .setTitleBgColor(Color.WHITE)
                .setDividerColor(Color.WHITE)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setCancelColor(Color.parseColor("#4A4A4A"))
                .setSubmitColor(Color.parseColor("#FF6B5C"))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(phoneCodeList);//添加数据源
        pvOptions.show();
    }
}
