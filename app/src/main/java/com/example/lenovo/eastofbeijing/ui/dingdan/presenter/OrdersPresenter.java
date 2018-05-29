package com.example.lenovo.eastofbeijing.ui.dingdan.presenter;

import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.bean.OrdersBean;
import com.example.lenovo.eastofbeijing.net.OrderApi;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.dingdan.contract.OrdersContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OrdersPresenter extends BasePresenter<OrdersContract.View> implements OrdersContract.Presenter {
    private OrderApi orderApi;

    @Inject
    public OrdersPresenter(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    @Override
    public void getOrders(String uid, String page, String token) {
        orderApi.getOrders(uid, page, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<OrdersBean, List<OrdersBean.DataBean>>() {
                    @Override
                    public List<OrdersBean.DataBean> apply(OrdersBean ordersBean) throws Exception {
                        return ordersBean.getData();
                    }
                }).subscribe(new Consumer<List<OrdersBean.DataBean>>() {
            @Override
            public void accept(List<OrdersBean.DataBean> dataBeans) throws Exception {
                if (mView != null) {
                    mView.showOrders(dataBeans);
                }
            }
        });
    }

    @Override
    public void getWaitOrders(String uid, String page, String token) {
        orderApi.getOrders(uid, page, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<OrdersBean, List<OrdersBean.DataBean>>() {
                    @Override
                    public List<OrdersBean.DataBean> apply(OrdersBean ordersBean) throws Exception {
                        return ordersBean.getData();
                    }
                }).subscribe(new Consumer<List<OrdersBean.DataBean>>() {
            @Override
            public void accept(List<OrdersBean.DataBean> dataBeans) throws Exception {
                List<OrdersBean.DataBean> list = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).getStatus() == 0) {
                        list.add(dataBeans.get(i));
                    }
                }
                if (mView != null) {
                    mView.showOrders(list);
                }
            }
        });
    }

    @Override
    public void getAlreadyOrders(String uid, String page, String token) {
        orderApi.getOrders(uid, page, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<OrdersBean, List<OrdersBean.DataBean>>() {
                    @Override
                    public List<OrdersBean.DataBean> apply(OrdersBean ordersBean) throws Exception {
                        return ordersBean.getData();
                    }
                }).subscribe(new Consumer<List<OrdersBean.DataBean>>() {
            @Override
            public void accept(List<OrdersBean.DataBean> dataBeans) throws Exception {
                List<OrdersBean.DataBean> list = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).getStatus() == 1) {
                        list.add(dataBeans.get(i));
                    }
                }
                if (mView != null) {
                    mView.showOrders(list);
                }
            }
        });
    }

    @Override
    public void getCancleOrders(String uid, String page, String token) {
        orderApi.getOrders(uid, page, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<OrdersBean, List<OrdersBean.DataBean>>() {
                    @Override
                    public List<OrdersBean.DataBean> apply(OrdersBean ordersBean) throws Exception {
                        return ordersBean.getData();
                    }
                }).subscribe(new Consumer<List<OrdersBean.DataBean>>() {
            @Override
            public void accept(List<OrdersBean.DataBean> dataBeans) throws Exception {
                List<OrdersBean.DataBean> list = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).getStatus() == 2) {
                        list.add(dataBeans.get(i));
                    }
                }
                if (mView != null) {
                    mView.showOrders(list);
                }
            }
        });
    }

    @Override
    public void updateOrder(String uid, String status, String orderId, String token) {
        orderApi.updateOrder(uid, status, orderId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {
                        if (mView!=null){
                            mView.updateOrderSuccess(baseBean);
                        }
                    }
                });
    }


}
