package cn.lattice.cabinet.network;

import cn.lattice.cabinet.base.BaseView;
import io.reactivex.disposables.Disposable;


public abstract class AbstractDialogSubscriber<T> extends AbstractSubscriber<T> {

    public AbstractDialogSubscriber(BaseView view) {
        super(view);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (view != null) view.dismissLoadingDialog();
    }


    @Override
    public void onComplete() {
        if (view != null) view.dismissLoadingDialog();
        super.onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (view != null) view.showLoadingDialog();
    }

}
