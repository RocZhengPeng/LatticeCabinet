package cn.lattice.cabinet.ui;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lattice.cabinet.R;
import cn.lattice.cabinet.adapter.BorrowAdapter;
import cn.lattice.cabinet.base.BaseActivity;
import cn.lattice.cabinet.entity.BorrowEntity;
import cn.lattice.cabinet.util.Util;

public class BorrowActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_borrow;
    }

    @Override
    protected void initComponents() {
        addSingleTitleBar("选择");

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        RecyclerView.ItemDecoration gridItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                final int spanCount = layoutManager.getSpanCount();
                int layoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                if (lp.getSpanSize() != spanCount) {
                    if (layoutPosition % 2 == 1) {
                        outRect.left = Util.dp2px(RocApplication.getContext(), 1);
                        outRect.right = Util.dp2px(RocApplication.getContext(), 1);
                    } else {
                        outRect.left = Util.dp2px(RocApplication.getContext(), 1);
                        outRect.right = Util.dp2px(RocApplication.getContext(), 1);
                    }
                }
                //outRect.top = divider;
            }
        };

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(gridItemDecoration);
        List<BorrowEntity> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new BorrowEntity());
        }
        BorrowAdapter borrowAdapter = new BorrowAdapter(R.layout.item_borrow, list);
        recyclerView.setAdapter(borrowAdapter);
    }

}
