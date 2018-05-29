package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.AdBean;
import com.example.lenovo.eastofbeijing.bean.BaseBean;
import com.example.lenovo.eastofbeijing.bean.CatagoryBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
   
    @GET("ad/getAd")
    Observable<AdBean> getAd();
    @FormUrlEncoded
    @POST("product/addCart")
    Observable<BaseBean> addCart(@Field("Uid") String uid,
                                 @Field("Pid") String pid,
                                 @Field("Token") String token);
    @GET("product/getCatagory")
    Observable<CatagoryBean> getCatagory();

}
