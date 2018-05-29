package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.AdBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AdApiService {
   
    @GET("ad/getAd")
    Observable<AdBean> getAd();
}
