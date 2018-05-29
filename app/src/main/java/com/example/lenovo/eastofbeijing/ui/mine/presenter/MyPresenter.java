package com.example.lenovo.eastofbeijing.ui.mine.presenter;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;
import com.example.lenovo.eastofbeijing.net.AdApi;
import com.example.lenovo.eastofbeijing.net.CatagoryApi;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.mine.contract.MyContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {
    private AdApi adApi;

    @Inject
    public MyPresenter(AdApi adApi) {
        this.adApi = adApi;
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
}
