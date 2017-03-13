package com.example.miaoz.xgpush.Activity;

import android.util.Log;

import com.example.miaoz.xgpush.network2.DsdServiceFactory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miaoz on 2017/3/13.
 */
public class Login2Activity extends BaseActivity {



    private void callAllMessage(String status) {

//        LoginBodyPost loginBody = new LoginBodyPost();
//        loginBody.setPhone("15821387671");
//        loginBody.setPassword("b476ceeb72001eedf5495324ba5ffa77");
        Map<String, String> mMap = new HashMap<>();
        mMap.put("phone","15821387671");
        mMap.put("password","b476ceeb72001eedf5495324ba5ffa77");
//        mMap.put("json","[{\"MsgType\":\"TIMTextElem\",\"MsgContent\":{\"Text\":\"q\"}}]");
        HashMap<String, String> map = (HashMap)new UrlFactory.PostParamBuilder().PostParamBuilder(mMap, true);
        DsdServiceFactory.getInstance().createService(ApiService.class)
                .getLoginInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("shandahua", "onStart: "+System.currentTimeMillis());
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("shandahua", "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("shandahua", "onError: -->>>>"+e.toString());
                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        Log.e("shandahua", "onNext: "+response );

                        HashMap<String, String> hashMap = new HashMap<>();
                        String content = "q";
                        JSONObject object = new JSONObject();
                        try {
                            object.put("MsgType","TIMTextElem");
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("Text","q");
                            object.put("MsgContent",jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String json = new Gson().toJson(object);
                        try {
                            JSONArray array = new JSONArray();
                            array.put(json);
                            String msg = new Gson().toJson(array);
                            hashMap.put("msgBody", msg);
                            Log.e("shandahua", "onNext: "+ URLEncoder.encode(msg,"UTF-8"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        OkAuthFactory.get().url("https://baidu.com").build().enqueue(new Callback() {
            @Override
            public void onRequestFail(Call call, Exception e) {
                Log.e("shandahua", "onRequestFail: "+e );
            }

            @Override
            public void onRequestSuccess(Call call, Response response) {
                Log.e("shandahua", "onRequestSuccess: response--->>>>"+response );
            }
        });

    }
}
