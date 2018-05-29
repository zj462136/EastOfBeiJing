package com.example.lenovo.eastofbeijing.ui.mine;

import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.inter.OnItemClickListener;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseFragment;
import com.example.lenovo.eastofbeijing.ui.details.ListDetailsActivity;
import com.example.lenovo.eastofbeijing.ui.dingdan.OrderListActivity;
import com.example.lenovo.eastofbeijing.ui.homepage.adapter.RvRecommendAdapter;
import com.example.lenovo.eastofbeijing.ui.login.LoginActivity;
import com.example.lenovo.eastofbeijing.ui.mine.contract.MyContract;
import com.example.lenovo.eastofbeijing.ui.mine.presenter.MyPresenter;
import com.example.lenovo.eastofbeijing.utils.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

    public class FragmentMy extends BaseFragment<MyPresenter> implements MyContract.View, View.OnClickListener {
    private View view;
    private ImageView my_user_icon;
    /**
     * 登录/注册 >
     */
    private TextView my_user_name;
    private LinearLayout my_linear_login;
    private ImageView my_xx;
    private RelativeLayout login_back_pic;
    private LinearLayout my_order_dfk;
    private LinearLayout my_order_dsh;
    private LinearLayout my_order_dpj;
    private LinearLayout my_order_th;
    private LinearLayout my_order_all;
    private RecyclerView tui_jian_recycler;
    private ScrollView fragment_my_scroll;
    private SmartRefreshLayout smart_refresh;
    public static final int HOMEPAGE_FRAGMENT = 0;
    @Override
    public int getContentLayout() {
        return R.layout.fragment_my;
    }
    @Override
    public void onResume() {
        super.onResume();
        String name = (String) SharedPreferencesUtils.getParam(getContext(), "name", "");
        String iconUrl = (String) SharedPreferencesUtils.getParam(getContext(), "iconUrl", "");
        String uid = (String) SharedPreferencesUtils.getParam(getContext(), "uid", "");
        if (!TextUtils.isEmpty(uid)) {
            //登录过
            login_back_pic.setBackgroundResource(R.drawable.normal_regbg);
        } else {
            //未登录
            login_back_pic.setBackgroundResource(R.drawable.reg_bg);
        }
        if (!TextUtils.isEmpty(iconUrl)) {
            Glide.with(getContext()).load(iconUrl).into(my_user_icon);
        }
        if (!TextUtils.isEmpty(name)) {
            my_user_name.setText(name);
        }
        mPresenter.getAd();
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
        my_user_icon = (ImageView) view.findViewById(R.id.my_user_icon);
        my_user_name = (TextView) view.findViewById(R.id.my_user_name);
        my_linear_login = (LinearLayout) view.findViewById(R.id.my_linear_login);
        my_xx = (ImageView) view.findViewById(R.id.my_xx);
        login_back_pic = (RelativeLayout) view.findViewById(R.id.login_back_pic);
        my_order_dfk = (LinearLayout) view.findViewById(R.id.my_order_dfk);
        my_order_dsh = (LinearLayout) view.findViewById(R.id.my_order_dsh);
        my_order_dpj = (LinearLayout) view.findViewById(R.id.my_order_dpj);
        my_order_th = (LinearLayout) view.findViewById(R.id.my_order_th);
        my_order_all = (LinearLayout) view.findViewById(R.id.my_order_all);
        tui_jian_recycler = (RecyclerView) view.findViewById(R.id.tui_jian_recycler);
        fragment_my_scroll = (ScrollView) view.findViewById(R.id.fragment_my_scroll);
        smart_refresh = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh);
        my_linear_login.setOnClickListener(this);
        my_order_dfk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_linear_login:
                //判断是否登录过
                String uid = (String) SharedPreferencesUtils.getParam(getContext(), "uid", "");
                if (TextUtils.isEmpty(uid)) {
                    //未登录
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    //已登录
                    Intent intent = new Intent(getContext(), UserInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_order_dfk:
                //跳转到我的订单页面
                Intent intent = new Intent(getContext(),OrderListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getAdSuccess(final AdBean adBean) {
        //设置布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        tui_jian_recycler.setLayoutManager(staggeredGridLayoutManager);
        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(getActivity(), adBean.getTuijian().getList());
        tui_jian_recycler.setAdapter(rvRecommendAdapter);
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
}
