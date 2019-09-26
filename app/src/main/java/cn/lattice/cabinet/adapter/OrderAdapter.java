package cn.lattice.cabinet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lattice.cabinet.entity.OrderEntity;

/**
 * Created by zhengpeng on 2019/9/13.
 */
public class OrderAdapter extends BaseQuickAdapter<OrderEntity, BaseViewHolder> {
    public OrderAdapter(int layoutResId, @Nullable List<OrderEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderEntity orderEntity) {

    }
}
