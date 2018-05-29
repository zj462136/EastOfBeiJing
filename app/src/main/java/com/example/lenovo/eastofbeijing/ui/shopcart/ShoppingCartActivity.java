package com.example.lenovo.eastofbeijing.ui.shopcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;
import com.example.lenovo.eastofbeijing.bean.eventbus.MessageEvent;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.inter.OnItemClickListener;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.dingdan.MakeSureOrderActivity;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.details.ListDetailsActivity;
import com.example.lenovo.eastofbeijing.ui.homepage.adapter.RvRecommendAdapter;
import com.example.lenovo.eastofbeijing.ui.login.LoginActivity;
import com.example.lenovo.eastofbeijing.ui.shopcart.adapter.ElvShoppingCartAdapter;
import com.example.lenovo.eastofbeijing.ui.shopcart.contract.ShoppingCartContract;
import com.example.lenovo.eastofbeijing.ui.shopcart.presenter.ShoppingCartPresenter;
import com.example.lenovo.eastofbeijing.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartContract.View, View.OnClickListener {

    private ImageView detail_image_back;
    private RelativeLayout text_title;
    private ExpandableListView elv;
    private ImageView empty_cart_image;
    /**
     * 登录
     */
    private Button cart_login;
    private LinearLayout linear_login;
    private RecyclerView rvRecommend;
    /**
     * 全选
     */
    private CheckBox cbAll;
    /**
     * 合计：
     */
    private TextView tvMoney;
    /**
     * 去结算：
     */
    private TextView tvTotal;
    public static final int HOMEPAGE_FRAGMENT = 0;
    private ProgressDialog progressDialog;
    private ElvShoppingCartAdapter adapter;
    private boolean isLogin=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter.getAd();
        progressDialog = DialogUtil.getProgressDialog(this);
    }
    public void initView() {
        detail_image_back = (ImageView) findViewById(R.id.detail_image_back);
        detail_image_back.setOnClickListener(this);
        text_title = (RelativeLayout) findViewById(R.id.text_title);
        elv = (ExpandableListView) findViewById(R.id.elv);
        empty_cart_image = (ImageView) findViewById(R.id.empty_cart_image);
        cart_login = (Button) findViewById(R.id.cart_login);
        linear_login = (LinearLayout) findViewById(R.id.linear_login);
        rvRecommend = (RecyclerView) findViewById(R.id.rvRecommend);
        cbAll = (CheckBox) findViewById(R.id.cbAll);
        cbAll.setOnClickListener(this);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTotal.setOnClickListener(this);
        if (isLogin) {
            elv.setVisibility(View.VISIBLE);
            linear_login.setVisibility(View.GONE);
            detail_image_back.setVisibility(View.VISIBLE);
            empty_cart_image.setVisibility(View.VISIBLE);
            mPresenter.getCarts(getUid(),getToken());
        }else {
            linear_login.setVisibility(View.VISIBLE);
            elv.setVisibility(View.GONE);
            empty_cart_image.setVisibility(View.GONE);
            cart_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShoppingCartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
    @Override
    public int getContentLayout() {
        return R.layout.activity_shop_cart;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void showCartList(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList) {
        adapter = new ElvShoppingCartAdapter(this,groupList,childList,progressDialog,mPresenter);
        if (adapter!=null){
            cbAll.setChecked(adapter.isAllSelected());
        }
        elv.setAdapter(adapter);
        for (int i=0;i<groupList.size();i++){
            elv.expandGroup(i);
        }
        if (adapter!=null){
            String[] moneyAndCount = adapter.getMoneyAndCount();
            tvMoney.setText("合计：￥" + moneyAndCount[0] + "元");
            tvTotal.setText("结算(" + moneyAndCount[1] + ")");
        }
    }
    @Override
    public void updateCartsSuccess(String msg) {
        if (adapter!=null){
            adapter.updateSuccess();
        }
    }
    @Override
    public void deleteCartsSuccess(String msg) {

    }
    @Override
    public void getAdSuccess(final AdBean adBean) {
        //设置布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        rvRecommend.setLayoutManager(staggeredGridLayoutManager);
        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(ShoppingCartActivity.this, adBean.getTuijian().getList());
        rvRecommend.setAdapter(rvRecommendAdapter);
        rvRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //跳转到详情页
                Intent intent = new Intent(ShoppingCartActivity.this, ListDetailsActivity.class);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.cbAll:
                if (adapter!=null){
                    adapter.changAllState(cbAll.isChecked());
                }
                break;
            case R.id.tvTotal:
                Intent intent1 = new Intent(this, MakeSureOrderActivity.class);
                startActivity(intent1);
                List<SellerBean> gList = adapter.getGroupList();
                List<List<GetCartsBean.DataBean.ListBean>> cList = adapter.getchildList();
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setcList(cList);
                messageEvent.setgList(gList);
                EventBus.getDefault().postSticky(messageEvent);
                break;
        }
    }
}
