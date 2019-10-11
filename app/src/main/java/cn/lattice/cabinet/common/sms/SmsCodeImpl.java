package cn.lattice.cabinet.common.sms;

import android.graphics.Color;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import cn.lattice.cabinet.ui.RocApplication;
import cn.lattice.cabinet.base.BaseView;
import cn.lattice.cabinet.entity.CodeEntity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.util.ToastUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Date: 2018/8/1.
 * Time: 12:04
 * classDescription:
 *
 * @author fred
 */
public class SmsCodeImpl implements SmsCode {
    private Disposable mDisposable;

    private Flowable flowable;

    private final BaseView baseView;

    public String uuid = null;
    /**
     * 找回密码
     */
    public static final String TYPE_PASS = "pass";
    /**
     * 设置支付密码
     */
    public static final String TYPE_SET_PAY = "set_pay";
    /**
     * 绑定银行卡
     */
    public static final String TYPE_BIND_CARD = "bind_card";


    public SmsCodeImpl(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void getCode(String phone, Button mBtCode) {

        RetrofitClient.getInstance(RocApplication.getContext()).sendCode(phone, new AbstractDialogSubscriber<CodeEntity>(baseView) {
            @Override
            public void onNext(CodeEntity entity) {
                if (entity.getStatus() == 1) {//成功
                    sendCodeSuccess(mBtCode);
                } else {//失败
                    ToastUtils.showShort(entity.getMsg());
                }
            }
        });
    }


    @Override
    public void sendCodeSuccess(Button mBtCode) {
        mBtCode.setEnabled(false);
        //倒计时
        flowable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    mBtCode.setText("重新获取(" + (60 - aLong) + ")");
                    mBtCode.setBackgroundColor(Color.parseColor("#ffcfcfcf"));
                })
                .doOnComplete(() -> {
                    mBtCode.setEnabled(true);
                    mBtCode.setText("获取验证码");
                    mBtCode.setBackgroundColor(Color.parseColor("#009fe3"));
                });

        mDisposable = flowable.subscribe();
    }

    /**
     * 停止倒计时
     *
     * @param mBtCode
     */
    public void release(Button mBtCode) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            //停止倒计时
            mDisposable.dispose();
            //重新订阅
            mDisposable = flowable.subscribe();
            //按钮可点击
            mBtCode.setEnabled(true);
            mBtCode.setText("获取验证码");
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
    }
}
