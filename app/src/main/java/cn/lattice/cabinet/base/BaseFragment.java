package cn.lattice.cabinet.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lattice.cabinet.util.ToastUtils;
import cn.lattice.cabinet.widget.TipDialog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description: Fragment 基类
 *
 * @author Dankal Android Developer
 * @since 2018/2/6
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected View mContentView;
    protected Unbinder unbinder;
    protected TipDialog loadingDialog;

    private CompositeDisposable mDisposables = new CompositeDisposable();
    protected boolean isVisibleToUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mContentView);

        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initComponents();
        obtainData();
    }

    /**
     * 一般用于加载网络请求
     * 此方法不是抽象方法，通过覆盖实现，可调用多次
     */
    public void obtainData() {
    }

    /**
     * onCreateView layout id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
    protected abstract void initComponents();


    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        loadingDialog = new TipDialog.Builder(getActivity())
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
        ToastUtils.showShort(message);
    }

    @Override
    public void addNetworkRequest(Disposable d) {
        mDisposables.add(d);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposables != null) mDisposables.clear();
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void tokenInvalid() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isVisibleToUser = !hidden;
    }
}
