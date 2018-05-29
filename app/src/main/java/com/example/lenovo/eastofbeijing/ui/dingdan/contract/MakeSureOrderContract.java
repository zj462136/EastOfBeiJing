package com.example.lenovo.eastofbeijing.ui.dingdan.contract;

import com.example.lenovo.eastofbeijing.bean.AddrsBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

import java.util.List;

public interface MakeSureOrderContract {
    interface View extends BaseContract.BaseView {
        void addrsSuccess(List<AddrsBean.DataBean> list);

        void createOrderSuccess(String msg);

        void updateCartsSuccess(String msg);

        void deleteCartSuccess(String msg);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getAddrs(String uid, String token);

        void createOrder(String uid, String price, String token);

        void updateCarts(String uid, String sellerid, String pid, String num, String selected, String token);

        void deleteCart(String uid, String pid, String token);

    }
}
