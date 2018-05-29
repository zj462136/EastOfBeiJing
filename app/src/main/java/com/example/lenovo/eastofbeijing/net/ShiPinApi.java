package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.ShiPinBean;

import io.reactivex.Observable;

public class ShiPinApi {
    private static ShiPinApi shiPinApi;
    private ShiPinApiService shiPinApiService;

    private ShiPinApi(ShiPinApiService shiPinApiService) {
        this.shiPinApiService = shiPinApiService;
    }

    public static ShiPinApi getShiPinApi(ShiPinApiService shiPinApiService) {
        if (shiPinApi == null) {
            shiPinApi = new ShiPinApi(shiPinApiService);
        }
        return shiPinApi;
    }

    public Observable<ShiPinBean> getShiPin(int type, int page) {
        return shiPinApiService.getShiPin(type,page);
    }
}
