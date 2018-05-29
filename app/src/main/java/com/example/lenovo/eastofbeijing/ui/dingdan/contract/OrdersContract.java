package com.example.lenovo.eastofbeijing.ui.dingdan.contract;

import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.bean.OrdersBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

import java.util.List;

public interface OrdersContract {

    interface View extends BaseContract.BaseView {
        void showOrders(List<OrdersBean.DataBean> list);

        void updateOrderSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getOrders(String uid, String page, String token);

        void getWaitOrders(String uid, String page, String token);

        void getAlreadyOrders(String uid, String page, String token);

        void getCancleOrders(String uid, String page, String token);

        void updateOrder(String uid, String status, String orderId, String token);
    }
}
