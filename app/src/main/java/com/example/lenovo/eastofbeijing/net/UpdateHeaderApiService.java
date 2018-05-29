package com.example.lenovo.eastofbeijing.net;

import com.example.lenovo.eastofbeijing.bean.BaseBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateHeaderApiService {

    @Multipart
    @POST("file/upload")
    Observable<BaseBean> updateHeader(@Part("uid") RequestBody uid, @Part MultipartBody.Part file);
}
