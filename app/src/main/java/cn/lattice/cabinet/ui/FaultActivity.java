package cn.lattice.cabinet.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.LoginEntity;
import cn.lattice.cabinet.login.LoginActivity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.ui.news.NewLoginActivity;
import cn.lattice.cabinet.ui.news.RechargeActivity;
import cn.lattice.cabinet.util.ToastUtils;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class FaultActivity extends BaseActivity {

    @BindView(R.id.et_device_number)
    EditText etDeviceNumber;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_function)
    TextView tvFunction;
    @BindView(R.id.iv_function_btn)
    ImageView ivFunctionBtn;
    @BindView(R.id.et_other_cause)
    EditText etOtherCause;
    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_tip)
    TextView ivTip;
    @BindView(R.id.ll_picture)
    LinearLayout llPicture;

    private int mCasue = 0;
    private File file;

    private Map<String, Object> map = new HashMap<>();

    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fault;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("上报故障");
        rxPermissions=new RxPermissions(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_upload, R.id.iv_scan, R.id.ll_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                String deviceNumber = etDeviceNumber.getText().toString().trim();
                if (TextUtils.isEmpty(deviceNumber)) {
                    ToastUtils.showShort("请输入设备编号");
                    return;
                }
                String userUUID = "";
                if (file != null) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("portrait", file.getName(), requestBody);
                    RetrofitClient.getInstance(this).addFault(userUUID, deviceNumber, mCasue, etOtherCause.getText().toString().trim(),
                            etQuestion.getText().toString().trim(), body, new AbstractDialogSubscriber<ResponseBody>(this) {
                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    try {
                                        String json = responseBody.string();
                                        JSONObject jsonObject = new JSONObject(json);
                                        int state = jsonObject.getInt("status");
                                        if (state == 1) {//成功
                                            //保存登录信息
                                            ToastUtils.showShort("提交成功");
                                        } else {//失败
                                            String message = jsonObject.getString("msg");
                                            ToastUtils.showShort(message);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    map.put("userId", userUUID);
                    map.put("deviceCode", deviceNumber);
                    map.put("cause", mCasue);
                    map.put("otherCause", etOtherCause.getText().toString().trim());
                    map.put("content", etQuestion.getText().toString().trim());
                    RetrofitClient.getInstance(this).addFault(map, new AbstractDialogSubscriber<ResponseBody>(this) {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String json = responseBody.string();
                                JSONObject jsonObject = new JSONObject(json);
                                int state = jsonObject.getInt("status");
                                if (state == 1) {//成功
                                    //保存登录信息
                                    ToastUtils.showShort("提交成功");
                                } else {//失败
                                    String message = jsonObject.getString("msg");
                                    ToastUtils.showShort(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.iv_scan:
                  rxPermissions.request(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(FaultActivity.this, CaptureActivity.class);
                                    startActivityForResult(intent, 1);
                                } else {
                                    ToastUtils.showShort("获取权限被拒绝");
                                    finish();
                                }
                            }
                        });
                break;
            case R.id.ll_picture:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                etDeviceNumber.setText(result);
                etDeviceNumber.setSelection(result.length());
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(FaultActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}
