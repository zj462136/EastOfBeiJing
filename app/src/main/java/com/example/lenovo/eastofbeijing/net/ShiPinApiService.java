package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.ShiPinBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShiPinApiService {
   
    @GET("satinApi")
    Observable<ShiPinBean> getShiPin(@Query("type") int type,@Query("page") int page);
}
