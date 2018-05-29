package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.CatagoryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CatagoryApiService {
    @GET("product/getCatagory")
    Observable<CatagoryBean> getCatagory();

}
