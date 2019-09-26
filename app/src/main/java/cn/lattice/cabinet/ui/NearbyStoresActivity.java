package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.NearbyAdapter;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.NearbyEntity;
import cn.lattice.cabinet.ui.news.NearbyActivity;

public class NearbyStoresActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby_stores;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("附近门店");
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<NearbyEntity> entityList=new ArrayList<>();
        entityList.add(new NearbyEntity());
        entityList.add(new NearbyEntity());
        entityList.add(new NearbyEntity());
        NearbyAdapter nearbyAdapter=new NearbyAdapter(R.layout.item_nearby_layout,entityList);
        recyclerView.setAdapter(nearbyAdapter);
        nearbyAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
             startActivity(new Intent(NearbyStoresActivity.this, NearbyActivity.class));
        });
    }

}
