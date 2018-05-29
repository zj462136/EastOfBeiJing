package com.example.lenovo.eastofbeijing.ui.dingdan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.AddrsBean;
import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;
import com.example.lenovo.eastofbeijing.bean.eventbus.MessageEvent;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.dingdan.adapter.MakeSureOrderAdapter;
import com.example.lenovo.eastofbeijing.ui.dingdan.contract.MakeSureOrderContract;
import com.example.lenovo.eastofbeijing.ui.dingdan.presenter.MakeSureOrderPresenter;
import com.example.lenovo.eastofbeijing.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MakeSureOrderActivity extends BaseActivity<MakeSureOrderPresenter> implements MakeSureOrderContract.View {
    private ProgressDialog progressDialog;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            amapLocation.getCity();//城市信息
            amapLocation.getDistrict();//城区信息
            amapLocation.getStreet();//街道信息
            amapLocation.getStreetNum();//街道门牌号信息
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private ImageView detail_image_back;
    private RelativeLayout detai_relative;
    /**
     * 收货人:
     */
    private TextView text_name;
    private TextView text_phone;
    private ImageView ding_wei_image;
    private TextView text_addr;
    private RelativeLayout relative_addr_01;
    private ExpandableListView elv;
    /**
     * 实付款:¥0.00
     */
    private TextView text_shi_fu_ku;
    /**
     * 提交订单
     */
    private TextView text_submit_order;
    private LinearLayout linear_bottom;
//    private ArrayList<GetCartsBean.DataBean.ListBean> list_selected;
//    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
//    private double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sure_order);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        EventBus.getDefault().register(this);
        //初始化dialog
        progressDialog = DialogUtil.getProgressDialog(this);
        initView();
//        list_selected = (ArrayList<GetCartsBean.DataBean.ListBean>) getIntent().getSerializableExtra("list_selected");
//        price = 0;
        //显示实付款...计算价格
//        for (int i = 0;i<list_selected.size(); i++) {
//            price += list_selected.get(i).getBargainPrice() * list_selected.get(i).getNum();
//        }
        //先去获取常用收货地址列表
        if (mPresenter != null) {
            mPresenter.getAddrs(getUid(), getToken());
//            mPresenter.createOrder(getUid(),price+"",getToken());
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_make_sure_order;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void addrsSuccess(List<AddrsBean.DataBean> list) {
        if (list != null && list.size() > 0) {
            //如果有数据，说明之前添加过地址
            toast("获取到地址列表");
            text_name.setText(list.get(0).getName());
            text_addr.setText(list.get(0).getAddr());

        } else {
            //如果没有数据，则没有则弹出一个dialog
            onGetDefaultAddrEmpty();
        }
    }

    @Override
    public void createOrderSuccess(String msg) {

    }

    @Override
    public void updateCartsSuccess(String msg) {
        progressDialog.dismiss();
    }

    @Override
    public void deleteCartSuccess(String msg) {

    }

    private void initView() {
        detail_image_back = (ImageView) findViewById(R.id.detail_image_back);
        detai_relative = (RelativeLayout) findViewById(R.id.detai_relative);
        text_name = (TextView) findViewById(R.id.text_name);
        text_phone = (TextView) findViewById(R.id.text_phone);
        ding_wei_image = (ImageView) findViewById(R.id.ding_wei_image);
        text_addr = (TextView) findViewById(R.id.text_addr);
        relative_addr_01 = (RelativeLayout) findViewById(R.id.relative_addr_01);
        elv = (ExpandableListView) findViewById(R.id.elv);
        text_shi_fu_ku = (TextView) findViewById(R.id.text_shi_fu_ku);
        text_submit_order = (TextView) findViewById(R.id.text_submit_order);
        linear_bottom = (LinearLayout) findViewById(R.id.linear_bottom);
        text_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createOrderPresenter.createOrder(ApiUtil.CREATE_ORDER_URL, CommonUtils.getString("uid"),price);
//                mPresenter.createOrder(getUid(),price+"",getToken());
                Intent intent = new Intent(MakeSureOrderActivity.this, OrderListActivity.class);
                startActivity(intent);
            }
        });
        detail_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(final MessageEvent event) {
        List<List<GetCartsBean.DataBean.ListBean>> cList = event.getcList();
        List<SellerBean> gList = event.getgList();
        MakeSureOrderAdapter adapter = new MakeSureOrderAdapter(MakeSureOrderActivity.this, gList, cList,
                progressDialog, mPresenter);
        elv.setAdapter(adapter);
        for (int i = 0; i < gList.size(); i++) {
            elv.expandGroup(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onGetDefaultAddrEmpty() {
        //弹出对话框...取消,,,finish/////确定...添加新地址...没添加点击了返回,当前确认订单页面finish,,,如果添加了显示地址
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeSureOrderActivity.this);
        builder.setTitle("提示")
                .setMessage("您还没有默认的收货地址,请设置收货地址")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //结束确认订单显示
                        MakeSureOrderActivity.this.finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //确定...跳转添加新地址...如果没有保存地址,确认订单finish,,,
                        //如果保存了地址...数据传回来进行显示(带有回传值的跳转)
                        Intent intent = new Intent(MakeSureOrderActivity.this, AddNewAddrActivity.class);
                        startActivityForResult(intent, 1001);
                    }
                })
                .show();
    }
}
