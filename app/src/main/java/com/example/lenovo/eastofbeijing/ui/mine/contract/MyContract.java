package com.example.lenovo.eastofbeijing.ui.mine.contract;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface MyContract {
    interface View extends BaseContract.BaseView {
        void getAdSuccess(AdBean adBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getAd();
    }

}
