package com.example.lenovo.eastofbeijing.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.ProductsBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.details.contract.AddCartContract;
import com.example.lenovo.eastofbeijing.ui.details.presenter.AddCartPresenter;
import com.example.lenovo.eastofbeijing.ui.listofgoods.ListActivity;
import com.example.lenovo.eastofbeijing.ui.login.LoginActivity;
import com.example.lenovo.eastofbeijing.ui.shopcart.ShoppingCartActivity;
import com.example.lenovo.eastofbeijing.utils.GlideImageLoader;
import com.example.lenovo.eastofbeijing.utils.ShareUtil;
import com.example.lenovo.eastofbeijing.utils.SharedPreferencesUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ListDetailsActivity extends BaseActivity<AddCartPresenter> implements View.OnClickListener,
        AddCartContract.View {
    String s1 = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
    private ImageView mIvShare;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private Banner mBanner;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private TextView mTvVipPrice;
    /**
     * 购物车
     */
    private TextView mTvShopCard;
    /**
     * 加入购物车
     */
    private TextView mTvAddCard;
    private int flag;
    private ProductsBean.DataBean bean;
    private AdBean.TuijianBean.ListBean listBean;
    private ImageView mImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //获取JavaBean
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
        if (flag == -1) {
            return;
        }
        if (flag == ListActivity.LISTACTIVITY) {
            bean = (ProductsBean.DataBean) intent.getSerializableExtra("bean");
        } else {
            listBean = (AdBean.TuijianBean.ListBean) intent.getSerializableExtra("bean");
        }
        //设置值
        setData();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_list_details;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    /**
     * 设置值
     */
    private void setData() {
        jcVideoPlayerStandard.setUp(s1, JCVideoPlayer.SCREEN_LAYOUT_NORMAL, "视频标题");
        Glide.with(getApplicationContext()).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(jcVideoPlayerStandard.thumbImageView);
        if (flag == ListActivity.LISTACTIVITY) {
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            String[] imgs = bean.getImages().split("\\|");
            //设置图片集合
            mBanner.setImages(Arrays.asList(imgs));
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
            mTvTitle.setText(bean.getTitle());
            //给原价加横线
            SpannableString spannableString = new SpannableString("原价:" + bean.getSalenum());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvPrice.setText(spannableString);
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(ListDetailsActivity.this, BannerDetailsActivity.class);
                    intent.putExtra("bean", bean);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            mTvVipPrice.setText("现价：" + bean.getPrice());
        } else {
            if (listBean == null) {
                return;
            }
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            String[] imgs = listBean.getImages().split("\\|");
            //设置图片集合
            mBanner.setImages(Arrays.asList(imgs));
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
            mTvTitle.setText(listBean.getTitle());
            //给原价加横线
            SpannableString spannableString = new SpannableString("原价:" + listBean.getSalenum());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvPrice.setText(spannableString);
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(ListDetailsActivity.this, BannerDetailsActivity.class);
                    intent.putExtra("bean", listBean);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            mTvVipPrice.setText("现价：" + listBean.getPrice());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddCard:
                //先判断是否登录
                String token = (String) SharedPreferencesUtils.getParam(ListDetailsActivity.this, "token", "");
                if (TextUtils.isEmpty(token)) {
                    //还未登录
                    //跳转到登录页面
                    Intent intent = new Intent(ListDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    //登录过了
                    String uid = (String) SharedPreferencesUtils.getParam(ListDetailsActivity.this, "uid", "");
                    int pid = bean.getPid();
                    mPresenter.addCart(uid, pid + "", token);
                }
                break;
            case R.id.tvShopCard:
                //跳转到购物车
                Intent intent = new Intent(ListDetailsActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.ivShare:
                if (flag == ListActivity.LISTACTIVITY) {
                    if (bean != null) {
                        ShareUtil.shareWeb(ListDetailsActivity.this, bean.getDetailUrl(), bean.getTitle(), "我在京东发现一个好的商品,赶紧来看看吧!", bean.getImages().split("\\|")[0], R.mipmap.ic_launcher);
                    }
                } else {
                    if (listBean != null) {
                        ShareUtil.shareWeb(ListDetailsActivity.this, listBean.getDetailUrl(), listBean.getTitle(), "我在京东发现一个好的商品,赶紧来看看吧!", listBean.getImages().split("\\|")[0], R.mipmap.ic_launcher);
                    }
                }
                break;
            case R.id.image_back:
                finish();
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(ListDetailsActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(ListDetailsActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(ListDetailsActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(ListDetailsActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onSuccess(String str) {
        Toast.makeText(ListDetailsActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    private void initView() {
        mIvShare = findViewById(R.id.ivShare);
        mIvShare.setOnClickListener(this);
        jcVideoPlayerStandard = findViewById(R.id.js);
        mBanner = findViewById(R.id.banner);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvPrice = findViewById(R.id.tvPrice);
        mTvVipPrice = findViewById(R.id.tvVipPrice);
        mTvShopCard = findViewById(R.id.tvShopCard);
        mTvShopCard.setOnClickListener(this);
        mTvAddCard = findViewById(R.id.tvAddCard);
        mTvAddCard.setOnClickListener(this);
        mImageBack = findViewById(R.id.image_back);
        mImageBack.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
