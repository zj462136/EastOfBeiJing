package com.example.lenovo.eastofbeijing.ui.find.presenter;

import com.example.lenovo.eastofbeijing.bean.ShiPinBean;
import com.example.lenovo.eastofbeijing.net.ShiPinApi;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.find.contract.FindContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FindPresenter extends BasePresenter<FindContract.View> implements FindContract.Presenter {
    private ShiPinApi shiPinApi;

    @Inject
    public FindPresenter(ShiPinApi shiPinApi) {
        this.shiPinApi = shiPinApi;
    }

    @Override
    public void shiPin(int type, int page) {
        shiPinApi.getShiPin(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShiPinBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShiPinBean shiPinBean) {
                        mView.onShiPinSuccess(shiPinBean);
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