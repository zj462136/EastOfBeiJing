package com.example.lenovo.eastofbeijing.ui.details.presenter;

import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.net.AddCartApi;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.details.contract.AddCartContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddCartPresenter extends BasePresenter<AddCartContract.View> implements AddCartContract.Presenter {
    private AddCartApi addCartApi;

    @Inject
    public AddCartPresenter(AddCartApi addCartApi) {
        this.addCartApi = addCartApi;
    }

    @Override
    public void addCart(String uid, String pid, String token) {
        addCartApi.getCatagory(uid, pid, token)
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
                    mView.onSuccess(s);
                }
            }
        });
    }
}
