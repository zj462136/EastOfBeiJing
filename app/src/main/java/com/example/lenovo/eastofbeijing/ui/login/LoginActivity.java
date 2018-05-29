package com.example.lenovo.eastofbeijing.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.UserBean;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.module.HttpModule;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.login.contract.LoginContract;
import com.example.lenovo.eastofbeijing.ui.login.presenter.LoginPresenter;
import com.example.lenovo.eastofbeijing.ui.regist.RegistActivity;
import com.example.lenovo.eastofbeijing.utils.SharedPreferencesUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {
    private RelativeLayout login_title_relative;
    /**
     * 请输入手机号
     */
    private EditText edit_phone;
    /**
     * 请输入密码
     */
    private EditText edit_pwd;
    /**
     * 手机快速注册
     */
    private TextView text_regist;
    private ImageView login_by_wechat;
    private ImageView login_by_qq;

    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }
    public void login(View view) {
        String phone = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();
        mPresenter.login(phone, pwd);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_regist:
                //跳转注册页面
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.login_by_wechat://微信登录

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);

                break;
            case R.id.login_by_qq://qq登录

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);

                break;
        }
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        Toast.makeText(this,userBean.getMsg(),Toast.LENGTH_SHORT).show();
        if ("0".equals(userBean.getCode())) {
            Toast.makeText(LoginActivity.this, userBean.getMsg(), Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(LoginActivity.this, "uid", userBean.getData().getUid() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "name", userBean.getData().getUsername() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "iconUrl", userBean.getData().getIcon() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "token", userBean.getData().getToken() + "");
            finish();
        }
    }

    private void initView() {
        login_title_relative = (RelativeLayout) findViewById(R.id.login_title_relative);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        text_regist = (TextView) findViewById(R.id.text_regist);
        text_regist.setOnClickListener(this);
        login_by_wechat = (ImageView) findViewById(R.id.login_by_wechat);
        login_by_wechat.setOnClickListener(this);
        login_by_qq = (ImageView) findViewById(R.id.login_by_qq);
        login_by_qq.setOnClickListener(this);
    }
    /**
     * 授权的监听事件
     */
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {


            String qq_uid = data.get("uid");
            String ni_cheng = data.get("name");
            String iconurl = data.get("iconurl");

            //实际上是根据这些qq提供的信息,去服务器拿到分配的临时账号,登录进京东的服务器
            //授权成功拿到信息模拟登录
            Log.i("----",ni_cheng+"--"+iconurl);

//            mPresenter.getLoginByQQ("15715317583","123456",ni_cheng,iconurl);


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(),                                  Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.i("-------------1111111","ddd");
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }*/

}

