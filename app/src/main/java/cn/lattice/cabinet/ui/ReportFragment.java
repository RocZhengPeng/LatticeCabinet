package cn.lattice.cabinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.OrderAdapter;
import cn.lattice.cabinet.base.BaseFragment;
import cn.lattice.cabinet.entity.OrderEntity;
import cn.lattice.cabinet.ui.news.RentDetailActivity;
import cn.lattice.cabinet.ui.news.SeeDetailActivity;


public class ReportFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private OrderAdapter orderAdapter;
    private List<OrderEntity> listEntity = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initComponents() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setEnableLastTime(true));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));

        listEntity.add(new OrderEntity());
        listEntity.add(new OrderEntity());
        listEntity.add(new OrderEntity());

        orderAdapter = new OrderAdapter(R.layout.item_order, listEntity);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(new Intent(getActivity(), RentDetailActivity.class));
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(orderAdapter);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }
}
