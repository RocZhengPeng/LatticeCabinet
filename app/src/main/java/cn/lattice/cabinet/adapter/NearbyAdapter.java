package cn.lattice.cabinet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lattice.cabinet.entity.NearbyEntity;

/**
 * Created by zhengpeng on 2019/7/2.
 */
public class NearbyAdapter extends BaseQuickAdapter<NearbyEntity, BaseViewHolder> {

    public NearbyAdapter(int layoutResId, @Nullable List<NearbyEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NearbyEntity item) {

    }
}
