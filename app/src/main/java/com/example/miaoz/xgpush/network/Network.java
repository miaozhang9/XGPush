package com.example.miaoz.xgpush.network;

import com.example.miaoz.xgpush.constant.UrlContainer;
import com.example.miaoz.xgpush.network.api.LoginApi;
import com.example.miaoz.xgpush.network.api.RegisterApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by miaoz on 2017/3/10.
 */
public class Network {
    private static LoginApi loginApi;
    private static RegisterApi registerApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static LoginApi getLoginApi() {
        if (loginApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(UrlContainer.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            loginApi = retrofit.create(LoginApi.class);

        }
        return loginApi;
    }

    public static RegisterApi getRegisterApi() {
        if (registerApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(UrlContainer.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            registerApi = retrofit.create(RegisterApi.class);
        }
        return registerApi;
    }






















}
