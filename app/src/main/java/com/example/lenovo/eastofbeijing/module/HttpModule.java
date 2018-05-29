package com.example.lenovo.eastofbeijing.module;

import com.example.lenovo.eastofbeijing.net.AdApi;
import com.example.lenovo.eastofbeijing.net.AdApiService;
import com.example.lenovo.eastofbeijing.net.AddCartApi;
import com.example.lenovo.eastofbeijing.net.AddCartApiService;
import com.example.lenovo.eastofbeijing.net.AddrsApi;
import com.example.lenovo.eastofbeijing.net.AddrsApiService;
import com.example.lenovo.eastofbeijing.net.Api;
import com.example.lenovo.eastofbeijing.net.CatagoryApi;
import com.example.lenovo.eastofbeijing.net.CatagoryApiService;
import com.example.lenovo.eastofbeijing.net.CreateOrderApi;
import com.example.lenovo.eastofbeijing.net.CreateOrderApiService;
import com.example.lenovo.eastofbeijing.net.DeleteCartApi;
import com.example.lenovo.eastofbeijing.net.DeleteCartApiService;
import com.example.lenovo.eastofbeijing.net.GetCartApi;
import com.example.lenovo.eastofbeijing.net.GetCartApiService;
import com.example.lenovo.eastofbeijing.net.ListApi;
import com.example.lenovo.eastofbeijing.net.ListApiService;
import com.example.lenovo.eastofbeijing.net.LoginApi;
import com.example.lenovo.eastofbeijing.net.LoginApiService;
import com.example.lenovo.eastofbeijing.net.MyInterceptor;
import com.example.lenovo.eastofbeijing.net.OrderApi;
import com.example.lenovo.eastofbeijing.net.OrderApiService;
import com.example.lenovo.eastofbeijing.net.ProductCatagoryApi;
import com.example.lenovo.eastofbeijing.net.ProductCatagoryApiService;
import com.example.lenovo.eastofbeijing.net.RegisterApi;
import com.example.lenovo.eastofbeijing.net.RegisterApiService;
import com.example.lenovo.eastofbeijing.net.ShiPinApi;
import com.example.lenovo.eastofbeijing.net.ShiPinApiService;
import com.example.lenovo.eastofbeijing.net.UpdateCartApi;
import com.example.lenovo.eastofbeijing.net.UpdateCartApiService;
import com.example.lenovo.eastofbeijing.net.UpdateHeaderApi;
import com.example.lenovo.eastofbeijing.net.UpdateHeaderApiService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

    @Provides
    LoginApi provideLoginApi(OkHttpClient.Builder builder) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        LoginApiService loginApiService = retrofit.create(LoginApiService.class);
        return LoginApi.getLoginApi(loginApiService);
    }

    @Provides
    AdApi provideAdApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AdApiService adApiService = retrofit.create(AdApiService.class);
        return AdApi.getAdApi(adApiService);
    }

    @Provides
    CatagoryApi provideCatagoryApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        CatagoryApiService catagoryApiService = retrofit.create(CatagoryApiService.class);
        return CatagoryApi.getCatagoryApi(catagoryApiService);
    }

    @Provides
    ProductCatagoryApi provideProductCatagoryApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        ProductCatagoryApiService productCatagoryApiService = retrofit.create(ProductCatagoryApiService.class);
        return ProductCatagoryApi.getProductCatagoryApi(productCatagoryApiService);
    }

    @Provides
    ListApi provideListApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        ListApiService listApiService = retrofit.create(ListApiService.class);
        return ListApi.getListApi(listApiService);
    }

    @Provides
    AddCartApi provideAddCartApi(OkHttpClient.Builder builder) {
        builder.addInterceptor(new MyInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AddCartApiService addCartApiService = retrofit.create(AddCartApiService.class);
        return AddCartApi.getAddCartApi(addCartApiService);
    }

    @Provides
    GetCartApi provideGetCartApi(OkHttpClient.Builder builder) {
        builder.addInterceptor(new MyInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        GetCartApiService getCartApiService = retrofit.create(GetCartApiService.class);
        return GetCartApi.getCartApi(getCartApiService);
    }

    @Provides
    UpdateCartApi provideUpdateCartApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        UpdateCartApiService updateCartApiService = retrofit.create(UpdateCartApiService.class);
        return UpdateCartApi.getUpdateCartApi(updateCartApiService);
    }

    @Provides
    DeleteCartApi provideDeleteCartApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        DeleteCartApiService deleteCartApiService = retrofit.create(DeleteCartApiService.class);
        return DeleteCartApi.getDeleteCartApi(deleteCartApiService);
    }

    @Provides
    AddrsApi provideAddrsApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AddrsApiService addrsApiService = retrofit.create(AddrsApiService.class);
        return AddrsApi.getAddrsApi(addrsApiService);
    }

    @Provides
    CreateOrderApi provideCreateOrderApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        CreateOrderApiService createOrderApiService = retrofit.create(CreateOrderApiService.class);
        return CreateOrderApi.getCreateOrderApi(createOrderApiService);
    }

    @Provides
    UpdateHeaderApi provideUpdateHeaderApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        UpdateHeaderApiService updateHeaderApiService = retrofit.create(UpdateHeaderApiService.class);
        return UpdateHeaderApi.getUpdateHeaderApi(updateHeaderApiService);
    }

    @Provides
    OrderApi provideOrderApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        OrderApiService orderApiService = retrofit.create(OrderApiService.class);
        return OrderApi.getOrderApi(orderApiService);
    }
    @Provides
    ShiPinApi provideShiPinApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        ShiPinApiService shiPinApiService = retrofit.create(ShiPinApiService.class);
        return ShiPinApi.getShiPinApi(shiPinApiService);
    }
    @Provides
    RegisterApi provideRegisterApi(OkHttpClient.Builder builder) {
        RegisterApiService registerApiService = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
                .create(RegisterApiService.class);
        return RegisterApi.getRegisterApi(registerApiService);
    }
}
