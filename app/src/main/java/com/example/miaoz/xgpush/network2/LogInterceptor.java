package com.example.miaoz.xgpush.network2;

import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Log.e("API Request", url);

        Response response = chain.proceed(request);
//        String json = JsonUtil.toJSON(response.body());
        String json = new Gson().toJson(response.body());
        Log.e("API Response", json);

        return response;
    }
}
