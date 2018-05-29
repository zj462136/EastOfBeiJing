package com.example.lenovo.eastofbeijing.ui.login.contract;

import com.example.lenovo.eastofbeijing.bean.UserBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.BaseView {
        void loginSuccess(UserBean userBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void login(String mobile, String password);
    }
}
