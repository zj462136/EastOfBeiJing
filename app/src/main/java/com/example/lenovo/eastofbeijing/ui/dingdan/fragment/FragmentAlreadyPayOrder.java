package com.example.lenovo.eastofbeijing.ui.dingdan.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.bean.OrdersBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.ui.base.BaseFragment;
import com.example.lenovo.eastofbeijing.ui.dingdan.adapter.OrderAdapter;
import com.example.lenovo.eastofbeijing.ui.dingdan.contract.OrdersContract;
import com.example.lenovo.eastofbeijing.ui.dingdan.presenter.OrdersPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class FragmentAlreadyPayOrder extends BaseFragment<OrdersPresenter> implements OrdersContract.View {
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private RelativeLayout relative_empty_order;
    //分页
    private int page = 1;
    private OrderAdapter adapter;
    private boolean isRefresh = true;//刷新状态

    @Override
    public int getContentLayout() {
        return R.layout.fragment_order_all_layout;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_order);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh);
        relative_empty_order = view.findViewById(R.id.relative_empty_order);

        //初始化Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        //设置适配器
        adapter = new OrderAdapter(getContext(),mPresenter);
        recyclerView.setAdapter(adapter);

        //请求接口获取订单
        mPresenter.getWaitOrders(getUid(), page + "", getToken());

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                isRefresh = true;
                mPresenter.getWaitOrders(getUid(), page + "", getToken());
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                isRefresh = false;
                mPresenter.getWaitOrders(getUid(), page + "", getToken());
            }
        });
    }

    @Override
    public void showOrders(List<OrdersBean.DataBean> list) {
        if (list == null || list.size() == 0) {
            relative_empty_order.setVisibility(View.VISIBLE);
            smartRefreshLayout.setVisibility(View.GONE);
            return;
        }
        smartRefreshLayout.setVisibility(View.VISIBLE);
        relative_empty_order.setVisibility(View.GONE);
        if (isRefresh) {
            if (adapter != null) {
                adapter.refresh(list);
                smartRefreshLayout.finishRefresh();
            }
        } else {
            if (adapter != null) {
                adapter.loadMore(list);
                smartRefreshLayout.finishLoadmore();
            }
            //判断当前列表的数据，是否大于等于总条目
            //   smartRefreshLayout.setLoadmoreFinished(true);
        }
    }

    @Override
    public void updateOrderSuccess(BaseBean baseBean) {

    }
}
