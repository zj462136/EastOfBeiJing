package com.example.lenovo.eastofbeijing.ui.listofgoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.ProductsBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.listofgoods.contract.ListContract;
import com.example.lenovo.eastofbeijing.ui.details.ListDetailsActivity;
import com.example.lenovo.eastofbeijing.ui.homepage.SearchActivity;
import com.example.lenovo.eastofbeijing.ui.listofgoods.presenter.ListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity<ListPresenter> implements ListContract.View, View.OnClickListener {
    private ImageView product_image_back;
    private LinearLayout linear_search;
    private ImageView image_change;
    private RecyclerView product_list_recycler;
    private RecyclerView product_grid_recycler;
    private SmartRefreshLayout refreshLayout;
    private int pscid;
    private String keywords;
    private boolean isRefresh = true;
    public static final int LISTACTIVITY = 1;
    private List<ProductsBean.DataBean> listAll = new ArrayList<>();
    private ProDuctListAdapter proDuctListAdapter;
    private ProDuctGridAdapter proDuctGridAdapter;
    //是否是列表展示
    private boolean isList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取pscid
        Intent intent = getIntent();
        pscid = intent.getIntExtra("pscid", 0);
        //接收传递的关键词
        keywords = getIntent().getStringExtra("keywords");
        initView();
        //        if (keywords != null) {
        //            //根据关键词和page去请求列表数据
        //
        //            mPresenter.getProducts(ApiUtil.SEARTCH_URL, keywords,page);
        //
        //        }
        mPresenter.getProducts(pscid + "");
    }

    private void initView() {
        product_image_back = (ImageView) findViewById(R.id.product_image_back);
        product_image_back.setOnClickListener(this);
        linear_search = (LinearLayout) findViewById(R.id.linear_search);
        linear_search.setOnClickListener(this);
        image_change = (ImageView) findViewById(R.id.image_change);
        image_change.setOnClickListener(this);
        product_list_recycler = (RecyclerView) findViewById(R.id.product_list_recycler);
        product_grid_recycler = (RecyclerView) findViewById(R.id.product_grid_recycler);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        //设置列表布局
        product_list_recycler.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        product_grid_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        //下拉刷新的监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新
                isRefresh = true;
                mPresenter.getProducts(pscid + "");
            }
        });
        //上拉加载的监听
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //加载更多
                isRefresh = false;
                mPresenter.getProducts(pscid + "");
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void onSuccess(List<ProductsBean.DataBean> list) {
        //先把数据添加到大集合
        listAll.addAll(list);
        //设置适配器就可以了
        setAdapter();
        //条目的点击事件 调到详情页面
        proDuctListAdapter.setOnListItemClickListener(new ProDuctListAdapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(ProductsBean.DataBean dataBean) {
                Intent intent = new Intent(ListActivity.this, ListDetailsActivity.class);
                intent.putExtra("bean", dataBean);
                intent.putExtra("flag", LISTACTIVITY);
                startActivity(intent);
            }
        });
        proDuctGridAdapter.setOnListItemClickListener(new ProDuctGridAdapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(ProductsBean.DataBean dataBean) {
                Intent intent = new Intent(ListActivity.this, ListDetailsActivity.class);
                intent.putExtra("bean", dataBean);
                intent.putExtra("flag", LISTACTIVITY);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        //设置列表设备器
        if (proDuctListAdapter == null) {
            proDuctListAdapter = new ProDuctListAdapter(ListActivity.this, listAll);
            product_list_recycler.setAdapter(proDuctListAdapter);
        } else {
            proDuctListAdapter.notifyDataSetChanged();
        }
        //设置表格适配器
        if (proDuctGridAdapter == null) {
            proDuctGridAdapter = new ProDuctGridAdapter(ListActivity.this, listAll);
            product_grid_recycler.setAdapter(proDuctGridAdapter);
        } else {
            proDuctGridAdapter.notifyDataSetChanged();
        }
        //停止刷新和加载更多
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_search:
                //搜索
                Intent intent1 = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.product_image_back:
                finish();
                break;
            case R.id.image_change:
                if (isList) {//表示当前展示的是列表..图标变成列表样式...表格进行显示,列表隐藏...isList---false
                    image_change.setImageResource(R.drawable.kind_liner);
                    product_grid_recycler.setVisibility(View.VISIBLE);
                    product_list_recycler.setVisibility(View.GONE);
                    isList = false;
                } else {
                    image_change.setImageResource(R.drawable.kind_grid);
                    product_list_recycler.setVisibility(View.VISIBLE);
                    product_grid_recycler.setVisibility(View.GONE);
                    isList = true;
                }
                break;
        }
    }
}
