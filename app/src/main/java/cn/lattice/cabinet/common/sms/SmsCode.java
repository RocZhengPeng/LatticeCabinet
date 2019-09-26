package cn.lattice.cabinet.common.sms;

import android.widget.Button;

/**
 * Date: 2018/8/1.
 * Time: 12:04
 * classDescription:
 * 具有获取验证码的页面管理
 *
 * @author fred
 */
public interface SmsCode {
    void getCode(String phone, Button mBtCode);

    void sendCodeSuccess(Button mBtCode);

    void onDestroy();
}
