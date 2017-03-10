package com.example.miaoz.xgpush.network.api;

import com.example.miaoz.xgpush.model.ResultReturn;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by miaoz on 2017/3/10.
 */
public interface LoginApi {
    @FormUrlEncoded
    @POST("/LoginServer/login.php")
    Observable<ResultReturn> login(@Field("email") String email, @Field("password") String password);
}
