package com.example.lenovo.eastofbeijing.ui.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dash.zxinglibrary.activity.CodeUtils;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.WebViewActivity;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.bean.ProductCatagoryBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.inter.OnItemClickListener;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseFragment;
import com.example.lenovo.eastofbeijing.ui.classify.adapter.ElvAdapter;
import com.example.lenovo.eastofbeijing.ui.classify.adapter.RvLeftAdapter;
import com.example.lenovo.eastofbeijing.ui.classify.contract.ClassifyContract;
import com.example.lenovo.eastofbeijing.ui.classify.presenter.ClassifyPresenter;
import com.example.lenovo.eastofbeijing.ui.homepage.CustomCaptrueActivity;
import com.example.lenovo.eastofbeijing.ui.homepage.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements ClassifyContract.View, View.OnClickListener {
    private View view;
    private ImageView mIvZxing;
    private LinearLayout mLinearSearch;
    private ImageView mImageNews;
    private RecyclerView mRvLeft;
    private ImageView mIv;
    private ExpandableListView mElv;
    private SmartRefreshLayout smart_refresh;
    @Override
    public int getContentLayout() {
        return R.layout.fragment_class;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        smart_refresh = view.findViewById(R.id.smart_refresh);
        mIvZxing = view.findViewById(R.id.ivZxing);
        mIvZxing.setOnClickListener(this);
        mLinearSearch = view.findViewById(R.id.linear_search);
        mLinearSearch.setOnClickListener(this);
        mImageNews = view.findViewById(R.id.image_news);
        mImageNews.setOnClickListener(this);
        mRvLeft = view.findViewById(R.id.rvLeft);
        mIv = view.findViewById(R.id.iv);
        mElv = view.findViewById(R.id.elv);
        mPresenter.getCatagory();

        //刷新
        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smart_refresh.finishLoadmore(2000);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smart_refresh.finishRefresh(2000);
            }
        });
    }

    @Override
    public void getProductCatagorySuccess(ProductCatagoryBean productCatagoryBean) {
        //定义一个集合用于存放title
        List<String> groupList = new ArrayList<>();
        //定义一个集合用于存放title对应的内容
        List<List<ProductCatagoryBean.DataBean.ListBean>> childList = new ArrayList<>();
        List<ProductCatagoryBean.DataBean> data = productCatagoryBean.getData();
        for (int i = 0; i < data.size(); i++) {
            groupList.add(data.get(i).getName());
            List<ProductCatagoryBean.DataBean.ListBean> list = data.get(i).getList();
            childList.add(list);
        }
        //使用ExpandableListView展示数据
        ElvAdapter elvAdapter = new ElvAdapter(getContext(), groupList, childList);
        mElv.setAdapter(elvAdapter);
        //默认展开列表
        for (int i = 0; i < groupList.size(); i++) {
            mElv.expandGroup(i);
        }

        productCatagoryBean.getMsg();

    }

    @Override
    public void getCatagorySuccess(final CatagoryBean catagoryBean) {
        final List<CatagoryBean.DataBean> data = catagoryBean.getData();
        //设置布局管理器
        mRvLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvLeft.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        //创建适配器
        final RvLeftAdapter adapter = new RvLeftAdapter(getContext(), data);
        //显示左侧列表数据
        mRvLeft.setAdapter(adapter);

        int cid = data.get(0).getCid();
        mPresenter.getProductCatagory(cid + "");

        //设置第一个为默认选中
        adapter.changeCheck(0, true);

        //设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //改变DataBean里面check的属性值
                adapter.changeCheck(position, true);
                //请求右侧对应的数据
                mPresenter.getProductCatagory(catagoryBean.getData().get(position).getCid() + "");
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ivZxing:
                //二维码
                Intent intent = new Intent(getActivity(), CustomCaptrueActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.linear_search:
                //搜索
                Intent intent1 = new Intent(getContext(), SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.image_news:
                Toast.makeText(getActivity(), "正在加载中……", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("http://")) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("detailUrl", result);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "暂不支持此二维码", Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
