package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.RegisterBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApiService {

    @FormUrlEncoded
    @POST("user/reg")
    Observable<RegisterBean> register(@Field("mobile") String mobile,
                                      @Field("password") String password);
}
