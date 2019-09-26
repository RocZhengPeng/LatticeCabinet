package cn.lattice.cabinet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lattice.cabinet.entity.BorrowEntity;

/**
 * Created by zhengpeng on 2019/6/30.
 */
public class BorrowAdapter extends BaseQuickAdapter<BorrowEntity, BaseViewHolder> {
    public BorrowAdapter(int layoutResId, @Nullable List<BorrowEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BorrowEntity item) {

    }
}
