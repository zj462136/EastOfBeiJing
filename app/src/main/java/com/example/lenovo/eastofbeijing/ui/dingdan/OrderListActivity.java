package com.example.lenovo.eastofbeijing.ui.dingdan;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentAllOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentAlreadyPayOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentCacelOrder;
import com.example.lenovo.eastofbeijing.ui.dingdan.fragment.FragmentWaitOrder;

public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView detail_image_back;
    private ImageView san_dian_pop;
    private RelativeLayout detai_relative;
    /**
     * 全部
     */
    private RadioButton radio_01;
    /**
     * 待支付
     */
    private RadioButton radio_02;
    /**
     * 已支付
     */
    private RadioButton radio_03;
    /**
     * 已取消
     */
    private RadioButton radio_04;
    private RadioGroup radio_group;
    private FrameLayout frame_content;
    private PopupWindow popupWindow;
    private TextView pop_dai_pay;
    private TextView pop_already_pay;
    private TextView pop_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public void inject() {

    }

    private void initView() {
        detail_image_back = (ImageView) findViewById(R.id.detail_image_back);
        san_dian_pop = (ImageView) findViewById(R.id.san_dian_pop);
        detai_relative = (RelativeLayout) findViewById(R.id.detai_relative);
        radio_01 = (RadioButton) findViewById(R.id.radio_01);
        radio_02 = (RadioButton) findViewById(R.id.radio_02);
        radio_03 = (RadioButton) findViewById(R.id.radio_03);
        radio_04 = (RadioButton) findViewById(R.id.radio_04);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        frame_content = (FrameLayout) findViewById(R.id.frame_content);
        detail_image_back.setOnClickListener(this);
        san_dian_pop.setOnClickListener(this);
        initPopUpWindown();

        int flag = getIntent().getIntExtra("flag", -1);

        if (flag == -1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentAllOrder()).commit();
        }else {
            if (flag == 1) {//待支付
                radio_group.check(R.id.radio_02);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentWaitOrder()).commit();
            }else if (flag == 2) {//已支付
                radio_group.check(R.id.radio_03);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentAlreadyPayOrder()).commit();
            }else if (flag == 3) {//已取消
                radio_group.check(R.id.radio_04);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentCacelOrder()).commit();
            }
        }



        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radio_01://全部
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentAllOrder()).commit();
                        break;
                    case R.id.radio_02://待支付
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentWaitOrder()).commit();
                        break;
                    case R.id.radio_03://已支付
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentAlreadyPayOrder()).commit();
                        break;
                    case R.id.radio_04://已取消
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentCacelOrder()).commit();
                        break;
                }
            }
        });
    }

    private void initPopUpWindown() {
        View view= View.inflate(OrderListActivity.this,R.layout.order_pop_layout,null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        pop_dai_pay = view.findViewById(R.id.pop_dai_pay);
        pop_already_pay = view.findViewById(R.id.pop_already_pay);
        pop_cancel = view.findViewById(R.id.pop_cancel);

        pop_dai_pay.setOnClickListener(this);
        pop_already_pay.setOnClickListener(this);
        pop_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.san_dian_pop:
                int checkedRadioButtonId = radio_group.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.radio_02://待支付
                        pop_dai_pay.setBackgroundColor(Color.BLUE);
                        pop_already_pay.setBackgroundColor(Color.WHITE);
                        pop_cancel.setBackgroundColor(Color.WHITE);
                        break;
                    case R.id.radio_03://已支付
                        pop_dai_pay.setBackgroundColor(Color.WHITE);
                        pop_already_pay.setBackgroundColor(Color.BLUE);
                        pop_cancel.setBackgroundColor(Color.WHITE);
                        break;
                    case R.id.radio_04://已取消
                        pop_dai_pay.setBackgroundColor(Color.WHITE);
                        pop_already_pay.setBackgroundColor(Color.WHITE);
                        pop_cancel.setBackgroundColor(Color.BLUE);
                        break;
                }

                popupWindow.showAsDropDown(san_dian_pop);

                break;
            case R.id.pop_dai_pay://待支付
                radio_group.check(R.id.radio_02);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentWaitOrder()).commit();
                popupWindow.dismiss();
                break;
            case R.id.pop_already_pay://已支付
                radio_group.check(R.id.radio_03);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentAlreadyPayOrder()).commit();
                popupWindow.dismiss();
                break;
            case R.id.pop_cancel://已取消
                radio_group.check(R.id.radio_04);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentCacelOrder()).commit();
                popupWindow.dismiss();
                break;
        }
    }
}
