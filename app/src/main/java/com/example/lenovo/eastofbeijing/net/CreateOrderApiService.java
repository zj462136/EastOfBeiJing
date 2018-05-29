package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CreateOrderApiService {
    @FormUrlEncoded
    @POST("product/createOrder")
    Observable<BaseBean> createOrder(@Field("Uid") String uid,
                                     @Field("Price") String price,
                                     @Field("Token") String token);

}
