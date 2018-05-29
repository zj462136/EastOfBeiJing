package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.UserBean;

import io.reactivex.Observable;

public class LoginApi {
    private static LoginApi loginApi;
    private LoginApiService loginApiService;

    private LoginApi(LoginApiService loginApiService) {
        this.loginApiService = loginApiService;
    }

    public static LoginApi getLoginApi(LoginApiService loginApiService) {
        if (loginApi == null) {
            loginApi = new LoginApi(loginApiService);
        }
        return loginApi;
    }

    public Observable<UserBean> login(String mobile, String password) {
        return loginApiService.login(mobile, password);
    }

}
