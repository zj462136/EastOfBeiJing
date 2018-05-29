package com.example.lenovo.eastofbeijing.ui.regist.contract;

import com.example.lenovo.eastofbeijing.bean.RegisterBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.BaseView {
        void registerSuccess(RegisterBean registerBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void register(String mobile, String password);
    }
}
