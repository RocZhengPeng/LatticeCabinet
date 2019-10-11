package cn.lattice.cabinet.login;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;
    private List<String> phoneCodeList = new ArrayList<>();

    private SmsCodeImpl smsCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("注册");
        phoneCodeList.add("0086");
        smsCode = new SmsCodeImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_code, R.id.btn_ok, R.id.tv_phone_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }

                smsCode.getCode(tvPhoneCode.getText().toString() + phone, btnCode);

                break;
            case R.id.btn_ok:
                String myPhone = etPhone.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                String messageCode = etCode.getText().toString().trim();

                RetrofitClient.getInstance(RocApplication.getContext()).register(tvPhoneCode.getText().toString() + myPhone, passWord, messageCode, new AbstractDialogSubscriber<ResponseBody>(this) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            int state = jsonObject.getInt("status");
                            if (state == 1) {//成功
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
