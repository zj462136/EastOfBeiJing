package com.example.lenovo.eastofbeijing.ui.homepage.contract;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface HomPageContract {
    interface View extends BaseContract.BaseView {
        void getAdSuccess(AdBean adBean);

        void getCatagorySuccess(CatagoryBean catagoryBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getAd();

        void getCatagory();
    }

}
