package com.example.miaoz.xgpush.network2;

import com.example.miaoz.xgpush.constant.UrlContainer;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author apple
 * @Description :
 * @date 17/3/6  上午11:01
 */

public class DsdServiceFactory extends OkHttpFactory {

    private DsdServiceFactory() {
        super(UrlContainer.BASE_URL);
    }


    @Override
    protected void onBuildClient(OkHttpClient.Builder clientBuilder) {

    }

    @Override
    protected void onBuildRetrofit(Retrofit.Builder retrofitBuilder) {
        /**
         * request to json
         */
        retrofitBuilder.addConverterFactory(new JsonStringConverterFactory(GsonConverterFactory.create()))
                /**
                 * response to json
                 */
                .addConverterFactory(GsonConverterFactory.create());

    }

    public static class FactoryInstance {
        private static final DsdServiceFactory INSTANCE = new DsdServiceFactory();
    }



    public static DsdServiceFactory getInstance() {
        return FactoryInstance.INSTANCE;
    }

}
