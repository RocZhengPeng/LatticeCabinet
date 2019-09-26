package cn.lattice.cabinet.ui.person;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.FragmentAdapter;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.ui.ReportFragment;

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    private List<Fragment> dataList;
    private List<String> titles;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initComponents() {
        ButterKnife.bind(this);

        addSingleTitleBar("我的订单");

        dataList = new ArrayList<>();
        dataList.add(getFragment(1));
        dataList.add(getFragment(2));

        titles = new ArrayList<>();
        titles.add("进行中");
        titles.add("进行时");

        viewPage.setAdapter(new FragmentAdapter(getSupportFragmentManager(), dataList, titles));

        tabLayout.setupWithViewPager(viewPage);

        for (int i = 0; i < titles.size(); i++) {
            tabLayout.getTabAt(i).setText(titles.get(i));
        }

    }

    private Fragment getFragment(int type) {
        ReportFragment fragment = new ReportFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
