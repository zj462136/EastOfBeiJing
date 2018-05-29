package com.example.lenovo.eastofbeijing.ui.shopcart.contract;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;
import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

import java.util.List;

public interface ShoppingCartContract {
    interface View extends BaseContract.BaseView{
        void showCartList(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList);
        void updateCartsSuccess(String msg);
        void deleteCartsSuccess(String msg);
        void getAdSuccess(AdBean adBean);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getCarts(String uid,String token);
        void updateCarts(String uid,String sellerid,String pid,String num,String selected,String token);
        void deleteCatr(String uid,String pid,String token);
        void getAd();
    }
}
