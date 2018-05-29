package com.example.lenovo.eastofbeijing.ui.classify.contract;

import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.bean.ProductCatagoryBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface ClassifyContract {
    interface View extends BaseContract.BaseView {
        void getProductCatagorySuccess(ProductCatagoryBean productCatagoryBean);

        void getCatagorySuccess(CatagoryBean catagoryBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getProductCatagory(String cid);

        void getCatagory();
    }
}
