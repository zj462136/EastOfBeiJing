package com.example.lenovo.eastofbeijing.ui.find;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.ShiPinBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseFragment;
import com.example.lenovo.eastofbeijing.ui.find.adapter.ShiAdapter;
import com.example.lenovo.eastofbeijing.ui.find.contract.FindContract;
import com.example.lenovo.eastofbeijing.ui.find.presenter.FindPresenter;
import com.example.lenovo.eastofbeijing.ui.homepage.SearchActivity;

import java.util.List;

public class FragmentFaXian extends BaseFragment<FindPresenter> implements FindContract.View, View.OnClickListener {

    private RecyclerView rv;
    private int type = 4;
    private int page = 1;
    private View view;
    private ImageView ivSousuo;
    private ImageView image_news;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_shipin;
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
        rv = (RecyclerView) view.findViewById(R.id.rv);
        mPresenter.shiPin(type, page);
        ivSousuo = (ImageView) view.findViewById(R.id.ivSousuo);
        ivSousuo.setOnClickListener(this);
        image_news = (ImageView) view.findViewById(R.id.image_news);
        image_news.setOnClickListener(this);
    }

    @Override
    public void onShiPinSuccess(ShiPinBean shiPinBean) {
        List<ShiPinBean.DataBean> data = shiPinBean.getData();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShiAdapter shiPinAdapter = new ShiAdapter(getActivity(), data);
        rv.setAdapter(shiPinAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ivSousuo:
                //搜索
                Intent intent1 = new Intent(getContext(), SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.image_news:
                Toast.makeText(getActivity(), "正在加载中……", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
