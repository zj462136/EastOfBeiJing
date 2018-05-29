package com.example.lenovo.eastofbeijing.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.zxinglibrary.activity.CodeUtils;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.WebViewActivity;
import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.inter.OnItemClickListener;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseFragment;
import com.example.lenovo.eastofbeijing.ui.details.ListDetailsActivity;
import com.example.lenovo.eastofbeijing.ui.homepage.adapter.RvClassAdapter;
import com.example.lenovo.eastofbeijing.ui.homepage.adapter.RvRecommendAdapter;
import com.example.lenovo.eastofbeijing.ui.homepage.adapter.RvSecKillAdapter;
import com.example.lenovo.eastofbeijing.ui.homepage.contract.HomPageContract;
import com.example.lenovo.eastofbeijing.ui.homepage.presenter.HomePagePresenter;
import com.example.lenovo.eastofbeijing.utils.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends BaseFragment<HomePagePresenter> implements HomPageContract.View, View
        .OnClickListener {
    private ImageView ivZxing;
    private LinearLayout linear_search;
    private ImageView image_news;
    private Banner banner;
    private RecyclerView rvClass;
    /**
     * 京东快报
     */
    private TextView tvJD;
    private LinearLayout llMore;
    private MarqueeView marqueeView;
    private RecyclerView rvSecKill;
    private RecyclerView rvRecommend;
    private SmartRefreshLayout smart_refresh;
    public static final int HOMEPAGE_FRAGMENT = 0;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
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
        //找控件
        smart_refresh = view.findViewById(R.id.smart_refresh);
        ivZxing = view.findViewById(R.id.ivZxing);
        ivZxing.setOnClickListener(this);
        linear_search = view.findViewById(R.id.linear_search);
        linear_search.setOnClickListener(this);
        image_news = view.findViewById(R.id.image_news);
        image_news.setOnClickListener(this);
        banner = view.findViewById(R.id.banner);
        rvClass = view.findViewById(R.id.rvClass);
        tvJD = view.findViewById(R.id.tvJD);
        llMore = view.findViewById(R.id.llMore);
        marqueeView = view.findViewById(R.id.marqueeView);
        rvSecKill = view.findViewById(R.id.rvSecKill);
        rvRecommend = view.findViewById(R.id.rvRecommend);
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

        List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是孙福生。");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：sfsheng0322");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 个人博客：sunfusheng.com");
        info.add("6. 微信公众号：孙福生");
        marqueeView.startWithList(info);

        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        rvClass.setLayoutManager(gridLayoutManager);

        //设置布局管理器
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rvSecKill.setLayoutManager(gridLayoutManager1);

        //设置布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        rvRecommend.setLayoutManager(staggeredGridLayoutManager);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //请求数据
        initData();
    }

    /**
     * 请求数据
     */
    private void initData() {
        mPresenter.getAd();
        mPresenter.getCatagory();
    }

    @Override
    public void getAdSuccess(final AdBean adBean) {
        final List<AdBean.DataBean> data = adBean.getData();
        //banner轮播图
        List<String> images = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            images.add(data.get(i).getIcon());
        }
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        //给Banner设置点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = data.get(position).getUrl();
                if (!TextUtils.isEmpty(url)) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("detailUrl", url);
                    startActivity(intent);
                }
            }
        });
        //秒杀
        RvSecKillAdapter rvSecKillAdapter = new RvSecKillAdapter(getActivity(), adBean.getMiaosha().getList());
        rvSecKill.setAdapter(rvSecKillAdapter);
        rvSecKillAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //跳转显示详情
                //获取地址
                String detailUrl = adBean.getMiaosha().getList().get(position).getDetailUrl();
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("detailUrl", detailUrl);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });

        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(getActivity(), adBean.getTuijian().getList());
        rvRecommend.setAdapter(rvRecommendAdapter);
        rvRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //跳转到详情页
                Intent intent = new Intent(getActivity(), ListDetailsActivity.class);
                AdBean.TuijianBean.ListBean listBean = adBean.getTuijian().getList().get(position);
                intent.putExtra("flag", HOMEPAGE_FRAGMENT);
                intent.putExtra("bean", listBean);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void getCatagorySuccess(CatagoryBean catagoryBean) {
        //分类
        final List<CatagoryBean.DataBean> data = catagoryBean.getData();
        RvClassAdapter rvClassAdapter = new RvClassAdapter(getActivity(), data);
        rvClass.setAdapter(rvClassAdapter);
        rvClassAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), data.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
