package com.example.miaoz.xgpush.network2;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author apple
 * @Description :
 * @date 17/3/6  上午10:37
 */

public  abstract class OkHttpFactory {

    private Retrofit retrofit;
    private HashMap<String, Object> apiMap = new HashMap<>();

    public OkHttpFactory(String url) {
        build(url, null);
    }

    public OkHttpFactory(String url, String cerConst) {
        build(url, cerConst);
    }

    private static SSLSocketFactory buildOkHttpFactory(String cerConst) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            int index = 0;
            String certificateAlias = Integer.toString(index++);
            InputStream certificate = new ByteArrayInputStream(cerConst.getBytes("UTF-8"));
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

            if (certificate != null) {
                try {
                    certificate.close();
                } catch (Exception e) {
                }
            }

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            return sslContext.getSocketFactory();

        } catch (Exception e) {
        }

        return null;
    }


    /**
     * Returns a SSlSocketFactory which trusts all certificates
     *
     * @return SSLSocketFactory
     */
    private static SSLSocketFactory getFixedSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };



            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { tm }, null);
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void build(String url, String cerConst) {

        ProguardUtil.checkNotNull(url,"baseUrl == null");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                /**
                 * log
                 */
//                .addInterceptor(new BodyInterceptor())
                .addNetworkInterceptor(new LogInterceptor())
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });

        if (!TextUtils.isEmpty(cerConst)) {
            clientBuilder.sslSocketFactory(buildOkHttpFactory(cerConst));
        } else {
            //默认通过所有证书认证
            clientBuilder.sslSocketFactory(getFixedSocketFactory());

        }

        onBuildClient(clientBuilder);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                /**
                 * server  url 必须endwith ／
                 */
                .baseUrl(url)   //.baseUrl(url, url.endsWith("/")) retrofit 原生库里没有这个boolean的防错判断 可自己扒库然后改方法
                /**
                 * call转observable
                 */
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientBuilder.build());

        onBuildRetrofit(retrofitBuilder);


        retrofit = retrofitBuilder.build();
    }

    public <T> T createService(Class<T> service) {
        String serviceName = service.getName();
        Object instance = apiMap.get(serviceName);
        if (instance == null) {
            synchronized (apiMap) {
                if (instance == null) {
                    instance = retrofit.create(service);
                    apiMap.put(serviceName, instance);
                }
            }
        }
        return instance == null ? null : (T) instance;
    }

    protected abstract void onBuildClient(OkHttpClient.Builder clientBuilder);

    protected abstract void onBuildRetrofit(Retrofit.Builder retrofitBiulder);

}
