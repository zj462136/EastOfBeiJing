package com.example.lenovo.eastofbeijing.ui.shopcart.presenter;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;
import com.example.lenovo.eastofbeijing.net.AdApi;
import com.example.lenovo.eastofbeijing.net.DeleteCartApi;
import com.example.lenovo.eastofbeijing.net.GetCartApi;
import com.example.lenovo.eastofbeijing.net.UpdateCartApi;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.shopcart.contract.ShoppingCartContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartContract.View> implements ShoppingCartContract.Presenter {
    private AdApi adApi;
    private GetCartApi getCartApi;
    private UpdateCartApi updateCartApi;
    private DeleteCartApi deleteCartApi;
    @Inject
    public ShoppingCartPresenter(AdApi adApi, GetCartApi getCartApi, UpdateCartApi updateCartApi, DeleteCartApi deleteCartApi) {
        this.adApi = adApi;
        this.getCartApi = getCartApi;
        this.updateCartApi = updateCartApi;
        this.deleteCartApi = deleteCartApi;
    }
    @Override
    public void getAd() {
        adApi.getAd()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AdBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdBean adBean) {
                        mView.getAdSuccess(adBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void getCarts(String uid, String token) {
        getCartApi.getCatagory(uid,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCartsBean>() {
                    @Override
                    public void accept(GetCartsBean getCartsBean) throws Exception {
                        List<SellerBean> groupList=new ArrayList<>();
                        List<List<GetCartsBean.DataBean.ListBean>> childList=new ArrayList<>();
                        List<GetCartsBean.DataBean> data = getCartsBean.getData();
                        if (data==null){
                            return;
                        }
                        for (int i=0;i<data.size();i++){
                            GetCartsBean.DataBean dataBean = data.get(i);
                            SellerBean sellerBean = new SellerBean();
                            sellerBean.setSellerName(dataBean.getSellerName());
                            sellerBean.setSellerid(dataBean.getSellerid());
                            sellerBean.setSelected(isSellerProductAllSelect(dataBean));
                            groupList.add(sellerBean);
                            List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
                            childList.add(list);
                            if (mView!=null){
                                mView.showCartList(groupList,childList);
                            }
                        }
                    }
                });
    }

    private boolean isSellerProductAllSelect(GetCartsBean.DataBean dataBean) {
        List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
        for (int i=0;i<list.size();i++) {
            GetCartsBean.DataBean.ListBean listBean = list.get(i);
            if (0==listBean.getSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateCarts(String uid, String sellerid, String pid, String num, String selected, String token) {
        updateCartApi.updateCarts(uid, sellerid, pid, num, selected, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.updateCartsSuccess(s);
                }
            }
        });
    }

    @Override
    public void deleteCatr(String uid, String pid, String token) {
        deleteCartApi.deleteCart(uid, pid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.updateCartsSuccess(s);
                }
            }
        });
    }
}
