package cn.lattice.cabinet.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.util.ActivityManager;
import cn.lattice.cabinet.util.DKHandler;
import cn.lattice.cabinet.util.TitleBarUtils;
import cn.lattice.cabinet.widget.TipDialog;
import cn.lattice.cabinet.widget.titlebar.ITitleBar;
import cn.lattice.cabinet.widget.titlebar.SingleTextTitle;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author Dankal Android Developer
 * @since 2018/7/3
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected TipDialog loadingDialog;


    //用来防止UI层销毁时，网络请求引发的内存泄漏
    private CompositeDisposable mDisposables = new CompositeDisposable();
    //protected CommonDialog commonDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushOneActivity(this);
     /*   if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());

            ButterKnife.bind(this);

            //初始化组件
            initComponents();

            obtainData();
        }

        //ActivityManager.getInstance().pushOneActivity(this);
    }


    /**
     * setContentView(LayoutResId)
     *
     * @return LayoutResId
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
    protected abstract void initComponents();

    /**
     * 添加标题栏
     */
    public void addTitleBar(ITitleBar iTitleBar) {
        if (iTitleBar == null) return;

        int titleBarResId = iTitleBar.getViewResId();

        View toolbarContainer = TitleBarUtils.init(this, titleBarResId);

        toolbarContainer.findViewById(R.id.iv_onback)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        iTitleBar.onBindTitleBar(toolbarContainer);
    }

    public void addSingleTitleBar(String title) {
        addTitleBar(new SingleTextTitle(title));
    }


    public void obtainData() {
    }

    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        loadingDialog = new TipDialog.Builder(this)
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        try {
            //ToastUtils.showShort(message);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void tokenInvalid() {
//        EMClient.getInstance().logout(true);
     /*   DKUserManager.resetToken();
        DKUserManager.resetUserInfo();
        ActivityUtils.finishAllActivities();
        try {
            startActivity(new Intent(this,
                    Class.forName(getString(R.string.login_activity_path))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void addNetworkRequest(Disposable d) {
        mDisposables.add(d);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) mDisposables.clear();
        ActivityManager.getInstance().popOneActivity(this);
    }

    @Override
    protected void onPause() {
      //  ToastUtils.cancel();
        super.onPause();
    }

    protected void postDelayedToFinish() {
        new DKHandler(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

}