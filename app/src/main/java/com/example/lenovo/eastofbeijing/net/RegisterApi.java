package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.RegisterBean;

import io.reactivex.Observable;

public class RegisterApi {
    private static RegisterApi registerApi;
    private RegisterApiService registerApiService;

    private RegisterApi(RegisterApiService registerApiService) {
        this.registerApiService = registerApiService;
    }

    public static RegisterApi getRegisterApi(RegisterApiService registerApiService) {
        if (registerApi == null) {
            registerApi = new RegisterApi(registerApiService);
        }
        return registerApi;
    }

    public Observable<RegisterBean> register(String mobile, String password) {
        return registerApiService.register(mobile, password);
    }
}
