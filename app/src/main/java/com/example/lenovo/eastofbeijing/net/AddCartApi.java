package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.BaseBean;

import io.reactivex.Observable;

public class AddCartApi {
    private static AddCartApi addCartApi;
    private AddCartApiService addCartApiService;

    private AddCartApi(AddCartApiService addCartApiService) {
        this.addCartApiService = addCartApiService;
    }

    public static AddCartApi getAddCartApi(AddCartApiService addCartApiService) {
        if (addCartApi == null) {
            addCartApi = new AddCartApi(addCartApiService);
        }
        return addCartApi;
    }

    public Observable<BaseBean> getCatagory(String uid, String pid, String token) {
        return addCartApiService.addCart(uid, pid, token);
    }

}
