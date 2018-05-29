package com.example.lenovo.eastofbeijing.ui.listofgoods.contract;

import com.example.lenovo.eastofbeijing.bean.ProductsBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

import java.util.List;

public interface ListContract {
    interface View extends BaseContract.BaseView {
        void onSuccess(List<ProductsBean.DataBean> list);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getProducts(String pscid);
    }
}
