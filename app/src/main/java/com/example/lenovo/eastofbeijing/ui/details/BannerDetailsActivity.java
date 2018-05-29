package com.example.lenovo.eastofbeijing.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.ProductsBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.widget.HackyViewPager;

import java.util.Arrays;
import java.util.List;

public class BannerDetailsActivity extends BaseActivity {

    private HackyViewPager mHvp;
    private TextView mTv;
    private ProductsBean.DataBean bean;
    private List<String> list;
    private int position;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取JavaBean
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        bean = (ProductsBean.DataBean) intent.getSerializableExtra("bean");
        String images = bean.getImages();
        String[] split = images.split("\\|");
        list = Arrays.asList(split);
        count = list.size();
        initView();

    }

    private void initView() {
        mHvp = (HackyViewPager) findViewById(R.id.hvp);
        mTv = (TextView) findViewById(R.id.tv);
        MyAdapter myAdapter = new MyAdapter(this, list);
        mHvp.setAdapter(myAdapter);

        mHvp.setCurrentItem(position);

        mTv.setText((position + 1) + "/" + count);

        mHvp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int p) {
                BannerDetailsActivity.this.position = p;
                mTv.setText((position + 1) + "/" + count);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_banner_details;
    }

    @Override
    public void inject() {

    }
}
