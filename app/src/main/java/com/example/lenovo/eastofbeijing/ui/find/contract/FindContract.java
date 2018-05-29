package com.example.lenovo.eastofbeijing.ui.find.contract;

import com.example.lenovo.eastofbeijing.bean.ShiPinBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface FindContract {
    interface View extends BaseContract.BaseView {
        void onShiPinSuccess(ShiPinBean shiPinBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void shiPin(int type,int page);
    }

}
