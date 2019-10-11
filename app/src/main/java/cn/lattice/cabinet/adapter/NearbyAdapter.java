package cn.lattice.cabinet.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lattice.cabinet.R;
import cn.lattice.cabinet.entity.AllStoreEntity;
import cn.lattice.cabinet.entity.NearbyEntity;
import cn.lattice.cabinet.util.DistanceUtil;
import cn.lattice.cabinet.util.SPUtils;

/**
 * Created by zhengpeng on 2019/7/2.
 */
public class NearbyAdapter extends BaseQuickAdapter<AllStoreEntity.DataBean, BaseViewHolder> {

    public NearbyAdapter(int layoutResId, @Nullable List<AllStoreEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllStoreEntity.DataBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_address_detail, item.getAddress());
        Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_image));
        String lng = (String) SPUtils.get("lng", "0.0");
        String lat = (String) SPUtils.get("lat", "0.0");
        String distance = DistanceUtil.getDistanceTow(Double.valueOf(lng), Double.valueOf(lat), Double.valueOf(item.getAddressLat()),Double.valueOf(item.getAddressLon()));
        helper.setText(R.id.tv_distance, "距离 " + distance);
    }
}
