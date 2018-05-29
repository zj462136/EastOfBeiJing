package com.example.lenovo.eastofbeijing.ui.regist.presenter;

import com.example.lenovo.eastofbeijing.bean.RegisterBean;
import com.example.lenovo.eastofbeijing.net.RegisterApi;
import com.example.lenovo.eastofbeijing.ui.base.BasePresenter;
import com.example.lenovo.eastofbeijing.ui.regist.contract.RegisterContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    RegisterApi registerApi;
    @Inject
    public RegisterPresenter(RegisterApi registerApi) {
        this.registerApi = registerApi;
    }

    @Override
    public void register(String mobile, String password) {
        registerApi.register(mobile, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        mView.registerSuccess(registerBean);
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
