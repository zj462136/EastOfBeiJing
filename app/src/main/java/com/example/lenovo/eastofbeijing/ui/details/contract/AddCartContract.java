package com.example.lenovo.eastofbeijing.ui.details.contract;

import com.example.lenovo.eastofbeijing.ui.base.BaseContract;

public interface AddCartContract {
    interface View extends BaseContract.BaseView {
        void onSuccess(String str);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void addCart(String uid, String pid, String token);
    }
}
