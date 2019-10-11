package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.NearbyAdapter;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.AllNetEntity;
import cn.lattice.cabinet.entity.AllStoreEntity;
import cn.lattice.cabinet.entity.NearbyEntity;
import cn.lattice.cabinet.network.AbstractDialogSubscriber;
import cn.lattice.cabinet.network.RetrofitClient;
import cn.lattice.cabinet.ui.news.NearbyActivity;

public class NearbyStoresActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<AllStoreEntity.DataBean> entityList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby_stores;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("附近门店");
        ButterKnife.bind(this);
        requestData();
    }


    private void requestData() {
        RetrofitClient.getInstance(RocApplication.getContext()).queryAllStore(new AbstractDialogSubscriber<AllStoreEntity>(NearbyStoresActivity.this) {
            @Override
            public void onNext(AllStoreEntity allStoreEntity) {
                entityList.addAll(allStoreEntity.getData());
                recyclerView.setLayoutManager(new LinearLayoutManager(NearbyStoresActivity.this));
                NearbyAdapter nearbyAdapter = new NearbyAdapter(R.layout.item_nearby_layout, entityList);
                recyclerView.setAdapter(nearbyAdapter);
                nearbyAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
                    startActivity(new Intent(NearbyStoresActivity.this, NearbyActivity.class));
                });
            }
        });
    }

}
