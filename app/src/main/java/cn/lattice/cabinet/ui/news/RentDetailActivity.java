package cn.lattice.cabinet.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.NearbyAdapter;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.NearbyEntity;
import cn.lattice.cabinet.ui.NearbyStoresActivity;

public class RentDetailActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent_detail;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("租赁详情");
        ButterKnife.bind(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        List<NearbyEntity> entityList = new ArrayList<>();
        entityList.add(new NearbyEntity());
        entityList.add(new NearbyEntity());
        entityList.add(new NearbyEntity());
        NearbyAdapter nearbyAdapter = new NearbyAdapter(R.layout.item_nearby_layout, entityList);
        rvList.setAdapter(nearbyAdapter);
        nearbyAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            startActivity(new Intent(RentDetailActivity.this, NearbyActivity.class));
        });
    }
}
