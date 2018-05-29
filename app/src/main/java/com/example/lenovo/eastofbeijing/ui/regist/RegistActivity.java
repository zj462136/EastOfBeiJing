package com.example.lenovo.eastofbeijing.ui.regist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.RegisterBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.regist.contract.RegisterContract;
import com.example.lenovo.eastofbeijing.ui.regist.presenter.RegisterPresenter;

public class RegistActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, View.OnClickListener {

    private ImageView cha_iamge;
    private RelativeLayout login_title_relative;
    /**
     * 请输入手机号
     */
    private EditText edit_phone;
    /**
     * 请输入密码
     */
    private EditText edit_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {
        String code = registerBean.getCode();
        if ("1".equals(code)) {
            Toast.makeText(RegistActivity.this,registerBean.getMsg(),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegistActivity.this,registerBean.getMsg(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        cha_iamge = (ImageView) findViewById(R.id.cha_iamge);
        cha_iamge.setOnClickListener(this);
        login_title_relative = (RelativeLayout) findViewById(R.id.login_title_relative);
        login_title_relative.setOnClickListener(this);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_phone.setOnClickListener(this);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        edit_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cha_iamge:

                finish();
                break;
        }
    }
    public void regist(View view) {

        String name = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();

       mPresenter.register(name,pwd);
    }
}
