package cn.lattice.cabinet.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.ui.RocApplication;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.common.sms.SmsCodeImpl;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.util.ToastUtils;
import okhttp3.ResponseBody;

public class RegisteredActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;

    private SmsCodeImpl smsCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("注册");
        smsCode = new SmsCodeImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_code, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }

                smsCode.getCode(phone, btnCode);

                break;
            case R.id.btn_ok:

                String myPhone = etPhone.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                String mailBox = etEmail.getText().toString().trim();
                String messageCode = etCode.getText().toString().trim();

                RetrofitClient.getInstance(RocApplication.getContext()).register(myPhone, passWord, name, mailBox, messageCode, new AbstractDialogSubscriber<ResponseBody>(this) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            int state = jsonObject.getInt("status");
                            if (state == 0) {//成功
                                ToastUtils.showShort("注册成功");
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
