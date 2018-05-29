package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddCartApiService {
    @FormUrlEncoded
    @POST("product/addCart")
    Observable<BaseBean> addCart(@Field("Uid") String uid,
                                 @Field("Pid") String pid,
                                 @Field("Token") String token);

}
